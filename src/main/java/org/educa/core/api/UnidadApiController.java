package org.educa.core.api;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.educa.core.dao.ExamenUnidadRepository;
import org.educa.core.dao.MaterialUnidadRepository;
import org.educa.core.dao.VideoUnidadRepository;
import org.educa.core.entities.model.ExamenUnidad;
import org.educa.core.entities.model.ExamenUnidadId;
import org.educa.core.entities.model.MaterialUnidad;
import org.educa.core.entities.model.VideoUnidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
		if(resultado==null){
			return new ResponseEntity<ExamenUnidad>(HttpStatus.NOT_FOUND);			
		}
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(resultado));
		return new ResponseEntity<ExamenUnidad>(resultado,HttpStatus.OK);	
	}

	

}
