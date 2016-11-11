package org.educa.core.api;

import java.io.ByteArrayInputStream;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

import org.educa.core.bean.ResponseBean;
import org.educa.core.bean.RespuestasExamenBean;
import org.educa.core.dao.ExamenUnidadRepository;
import org.educa.core.dao.MaterialUnidadRepository;
import org.educa.core.dao.SesionUsuarioRepository;
import org.educa.core.dao.VideoUnidadRepository;
import org.educa.core.entities.model.EstadoExamen;
import org.educa.core.entities.model.EstadoSesionUsuario;
import org.educa.core.entities.model.Evaluacion;
import org.educa.core.entities.model.ExamenUnidad;
import org.educa.core.entities.model.ExamenUnidadId;
import org.educa.core.entities.model.MaterialUnidad;
import org.educa.core.entities.model.PreguntaExamenUnidad;
import org.educa.core.entities.model.SesionUsuario;
import org.educa.core.entities.model.SesionUsuarioId;
import org.educa.core.entities.model.VideoUnidad;
import org.educa.core.exceptions.YaExisteException;
import org.educa.core.services.EvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/unidad")
public class UnidadApiController {
	
	private static final int PRIMER_EXAMEN_ID = 1;

	@Autowired
	private VideoUnidadRepository videoUnidadRepository;
	
	@Autowired
	private MaterialUnidadRepository materialUnidadRepository;
	
	@Autowired
	private ExamenUnidadRepository examenUnidadRepository;
	
	
	@Autowired
	private EvaluacionService evaluacionService;
	
	@Autowired
	private SesionUsuarioRepository sesionUsuarioRepository;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "{numeroUnidad}/{idCurso}/video", headers="Accept=*/*",produces = {"video/mp4"})
	public ResponseEntity<InputStreamResource> video(@PathVariable Integer numeroUnidad, @PathVariable Long idCurso) {
		List<VideoUnidad> resultado = videoUnidadRepository.findByNumeroAndIdCurso(numeroUnidad, idCurso);
		if(resultado == null || resultado.isEmpty()){
			return new ResponseEntity<InputStreamResource>(HttpStatus.NOT_FOUND);			
		}		
		return ResponseEntity
	            .ok()
	            .contentType(
	                    MediaType.parseMediaType("video/mp4"))
	            .body(new InputStreamResource(new ByteArrayInputStream(resultado.iterator().next().getVideo())));		
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "{numeroUnidad}/{idCurso}/material", headers="Accept=*/*",produces = {"text/html"})
	public ResponseEntity<InputStreamResource> material(@PathVariable Integer numeroUnidad, @PathVariable Long idCurso) {
		List<MaterialUnidad> resultado = materialUnidadRepository.findByNumeroAndIdCurso(numeroUnidad, idCurso);
		if(resultado == null || resultado.isEmpty()){
			return new ResponseEntity<InputStreamResource>(HttpStatus.NOT_FOUND);			
		}		
		return ResponseEntity
	            .ok()
	            .contentType(
	                    MediaType.parseMediaType("text/html"))
	            .body(new InputStreamResource(new ByteArrayInputStream(resultado.iterator().next().getMaterial())));		
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "{numeroUnidad}/{idCurso}/examen", produces = {"application/json"})
	public ResponseEntity<ExamenUnidad> examen(@PathVariable Integer numeroUnidad, @PathVariable Long idCurso) throws JsonProcessingException {
		ExamenUnidadId id = new ExamenUnidadId();
		id.setIdCurso(idCurso);
		id.setIdExamen(PRIMER_EXAMEN_ID);
		id.setNumero(numeroUnidad);
		ExamenUnidad resultado = examenUnidadRepository.findOne(id);
		if(resultado==null || !resultado.isCompleto()){
			return new ResponseEntity<ExamenUnidad>(HttpStatus.NOT_FOUND);			
		}
		ObjectMapper mapper = new ObjectMapper();
		List<PreguntaExamenUnidad> preguntas = resultado.getPreguntas();
		Collections.shuffle(preguntas);
		preguntas = preguntas.subList(0, resultado.getCantPreguntasUsuario());
		resultado.setPreguntas(preguntas);
		return new ResponseEntity<ExamenUnidad>(resultado,HttpStatus.OK);	
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "enviarResultado", produces = {"application/json"})
	public ResponseEntity<Evaluacion> evaluacion(											
				@RequestBody RespuestasExamenBean respuestasBean) throws JsonProcessingException {
		
		
		Evaluacion evaluacion = new Evaluacion();
		
		try {
			evaluacion = evaluacionService.evaluar(respuestasBean);
			String porcentaje = calcularPorcenta(evaluacion);
			evaluacion.setPorcentaje(porcentaje);
			if(evaluacion.getEstado().equals(EstadoExamen.DESAPROBADO)){
				SesionUsuarioId sesionUsuarioId = new SesionUsuarioId();
				sesionUsuarioId.setId(Long.valueOf(respuestasBean.getIdUsuario()));
				sesionUsuarioId.setIdCurso(Long.valueOf(respuestasBean.getIdCurso()));
				sesionUsuarioId.setNumero(respuestasBean.getIdSesion());
				
				SesionUsuario sesionUsuario = sesionUsuarioRepository.findOne(sesionUsuarioId);
				sesionUsuario.setDesaprobado(true);
				sesionUsuario.setEstado(EstadoSesionUsuario.DESAPROBADO);
				sesionUsuarioRepository.save(sesionUsuario);
				
			} else {
				// verificar si aprobo todos los examentes
			}
		} catch (YaExisteException e) {
			return new ResponseEntity<Evaluacion>(evaluacion, HttpStatus.CONFLICT);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<Evaluacion>(evaluacion, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Evaluacion>(evaluacion,HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "consultarExamen/{idUsuario}/{idSesion}/{idCurso}/{numeroUnidad}", produces = {"application/json"})
	public ResponseEntity<Evaluacion> evaluacion(@PathVariable Integer idUsuario,
											@PathVariable Integer idSesion,
											@PathVariable Integer idCurso,
											@PathVariable Integer numeroUnidad) throws JsonProcessingException {
		
		
		Evaluacion evaluacion = new Evaluacion();
		
		
		try {
			evaluacion = evaluacionService.consultarEvaluacion(idSesion, idUsuario, idCurso, numeroUnidad);
			String porcentaje = calcularPorcenta(evaluacion);
			evaluacion.setPorcentaje(porcentaje);			
		} catch (Exception e) {
			return new ResponseEntity<Evaluacion>(evaluacion, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Evaluacion>(evaluacion,HttpStatus.OK);
	}

	private String calcularPorcenta(Evaluacion evaluacion) {
		double nota = (double)evaluacion.getCantidadRespuestasCorrectas() / (double)evaluacion.getCantidadPreguntasTotales();
		double porcentajeNota = nota * 100;
		DecimalFormat df = new DecimalFormat("#.00");
		String porcentaje = df.format(porcentajeNota);
		return porcentaje;
	}

	

}
