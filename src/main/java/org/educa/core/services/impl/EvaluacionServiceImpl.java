package org.educa.core.services.impl;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.educa.core.bean.RespuestaExamenBean;
import org.educa.core.bean.RespuestasExamenBean;
import org.educa.core.dao.EvaluacionRepository;
import org.educa.core.entities.model.EstadoExamen;
import org.educa.core.entities.model.Evaluacion;
import org.educa.core.entities.model.ExamenUnidad;
import org.educa.core.exceptions.YaExisteException;
import org.educa.core.services.EvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluacionServiceImpl implements EvaluacionService {

	@Autowired
	private EvaluacionRepository evaluacionRepository;
	
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
			
			/*if (examen.getCantPreguntasUsuario() != respuestasBean.getRespuestas().size()) {
				evaluacion.setEstado(EstadoExamen.INCOMPLETO);
			} else {
				double nota = (double)cantRespuestasCorrectas / (double)examen.getCantPreguntasUsuario();
				if (nota >= 0.6) {
					evaluacion.setEstado(EstadoExamen.APROBADO);
				} else {
					evaluacion.setEstado(EstadoExamen.DESAPROBADO);
				}
				
			}*/
			
			
			
			evaluacion = evaluacionRepository.save(evaluacion);
		} else {
			throw new YaExisteException();
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

	public class EvaluacionComparator implements Comparator<Evaluacion> {
	    @Override
	    public int compare(Evaluacion o1, Evaluacion o2) {
	        return (o1.getFechaActualizacion().compareTo(o2.getFechaActualizacion())*(-1));
	    }
	}

}
