package org.educa.core.services.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.educa.core.bean.RespuestaExamenBean;
import org.educa.core.bean.RespuestasExamenBean;
import org.educa.core.dao.CursoRepository;
import org.educa.core.dao.EvaluacionRepository;
import org.educa.core.dao.SesionUsuarioRepository;
import org.educa.core.dao.UsuarioDao;
import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.EstadoExamen;
import org.educa.core.entities.model.EstadoSesionUsuario;
import org.educa.core.entities.model.Evaluacion;
import org.educa.core.entities.model.ExamenUnidad;
import org.educa.core.entities.model.SesionUsuario;
import org.educa.core.entities.model.SesionUsuarioId;
import org.educa.core.entities.model.Unidad;
import org.educa.core.exceptions.YaExisteException;
import org.educa.core.services.EvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.fabric.xmlrpc.base.Array;

@Service
public class EvaluacionServiceImpl implements EvaluacionService {

	@Autowired
	private EvaluacionRepository evaluacionRepository;
	
	@Autowired
	private SesionUsuarioRepository sesionUsuarioRepository;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Override
	public Evaluacion evaluar(RespuestasExamenBean respuestasBean) throws Exception {

		Evaluacion evaluacion = null;
		
		List<Evaluacion> evaluaciones = evaluacionRepository.findByIdUsuarioAndIdSesionAndIdCursoAndNumeroUnidad(
					respuestasBean.getIdUsuario(), respuestasBean.getIdSesion(), respuestasBean.getIdCurso(), respuestasBean.getNumeroUnidad());
		if (evaluaciones.isEmpty()) {
			Date fechaCreacion = Calendar.getInstance().getTime(); 
			evaluacion = new Evaluacion();
			evaluacion.setIdUsuario(respuestasBean.getIdUsuario());
			evaluacion.setIdSesion(respuestasBean.getIdSesion());
			evaluacion.setIdCurso(respuestasBean.getIdCurso());
			evaluacion.setNumeroUnidad(respuestasBean.getNumeroUnidad());
			evaluacion.setCantidadRespuestasCorrectas(respuestasBean.getCantDePregAprobadas());
			evaluacion.setCantidadPreguntasTotales(respuestasBean.getCantDePreguntas());
			evaluacion.setFechaActualizacion(fechaCreacion);
			if (respuestasBean.getEstado().equals("INCOMPLETO")) {
				evaluacion.setEstado(EstadoExamen.INCOMPLETO);
			} else if (respuestasBean.getEstado().equals("APROBADO")) {
				evaluacion.setEstado(EstadoExamen.APROBADO);
			} else if (respuestasBean.getEstado().equals("DESAPROBADO")) {
				evaluacion.setEstado(EstadoExamen.DESAPROBADO);
			}		
			evaluacion = evaluacionRepository.save(evaluacion);
		} else {
			throw new YaExisteException();
		}
		
		// Aprobamos o rechazamos el curso
		String porcentaje = calcularPorcenta(evaluacion);
		evaluacion.setPorcentaje(porcentaje);
		if(evaluacion.getEstado().equals(EstadoExamen.DESAPROBADO)){
			actualizarAprobacionCurso(respuestasBean, EstadoSesionUsuario.DESAPROBADO);
		} else if(evaluacion.getEstado().equals(EstadoExamen.APROBADO)) {
			Curso unCurso = cursoRepository.findOne(new Long(respuestasBean.getIdCurso()));
			evaluaciones = this.consultarEvaluaciones(respuestasBean.getIdSesion(), respuestasBean.getIdUsuario(), respuestasBean.getIdCurso());
			int cantExamenesPendiente = 0;
			for (Unidad unidad : unCurso.getUnidades()) {
				if (unidad.isPublicado()) {
					cantExamenesPendiente++;	
				}
			}
			
			for (Unidad unidad : unCurso.getUnidades()) {
				if (unidad.isPublicado()) {
					for (Evaluacion unaEvaluacion : evaluaciones) {
						if (unaEvaluacion.getNumeroUnidad() == unidad.getId().getNumero() &&
								unaEvaluacion.getEstado().equals(EstadoExamen.APROBADO)){
							cantExamenesPendiente--;
						}
					}
				}
			}
			
			if (cantExamenesPendiente <= 0) {
				actualizarAprobacionCurso(respuestasBean, EstadoSesionUsuario.APROBADO);
				evaluacion.setEstadoSesionUsuario(EstadoSesionUsuario.APROBADO);
			} 
		}

		return evaluacion;
	}

	@Override
	public Evaluacion consultarEvaluacion(Integer numero, Integer idUsuario, Integer idCurso, Integer numeroUnidad) throws Exception {
		
		Evaluacion evaluacion = null;
		List<Evaluacion> evaluaciones = evaluacionRepository.findByIdUsuarioAndIdSesionAndIdCursoAndNumeroUnidad(
										idUsuario, numero, idCurso, numeroUnidad);
		if (!evaluaciones.isEmpty()) {
			Collections.sort(evaluaciones, new EvaluacionComparator());
			evaluacion = evaluaciones.get(0);
		} else {
			throw new Exception();
		}
		return evaluacion;
	}
	
	@Override
	public List<Evaluacion> consultarEvaluaciones(Integer numero, Integer idUsuario, Integer idCurso) throws Exception {
		
		List<Evaluacion> evaluaciones = evaluacionRepository.findByIdUsuarioAndIdSesionAndIdCurso(idUsuario, numero, idCurso);
		if (evaluaciones.isEmpty()) {
			evaluaciones = new ArrayList<Evaluacion>();
		} 
		return evaluaciones;
	}

	public class EvaluacionComparator implements Comparator<Evaluacion> {
	    @Override
	    public int compare(Evaluacion o1, Evaluacion o2) {
	        return (o1.getFechaActualizacion().compareTo(o2.getFechaActualizacion())*(-1));
	    }
	}
	
	
	private void actualizarAprobacionCurso(RespuestasExamenBean respuestasBean, EstadoSesionUsuario estado) {
		SesionUsuarioId sesionUsuarioId = new SesionUsuarioId();
		sesionUsuarioId.setId(Long.valueOf(respuestasBean.getIdUsuario()));
		sesionUsuarioId.setIdCurso(Long.valueOf(respuestasBean.getIdCurso()));
		sesionUsuarioId.setNumero(respuestasBean.getIdSesion());
		
		SesionUsuario sesionUsuario = sesionUsuarioRepository.findOne(sesionUsuarioId);
		
		if (estado.equals(EstadoSesionUsuario.DESAPROBADO)) {
			sesionUsuario.setDesaprobado(true);
		} else {
			sesionUsuario.setDesaprobado(false);
		}
		sesionUsuario.setEstado(estado);
		sesionUsuarioRepository.save(sesionUsuario);
	}
	
	private String calcularPorcenta(Evaluacion evaluacion) {
		double nota = (double)evaluacion.getCantidadRespuestasCorrectas() / (double)evaluacion.getCantidadPreguntasTotales();
		double porcentajeNota = nota * 100;
		DecimalFormat df = new DecimalFormat("#.00");
		String porcentaje = df.format(porcentajeNota);
		return porcentaje;
	}

}
