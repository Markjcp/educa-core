package org.educa.core.services.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.educa.core.bean.RespuestaExamenBean;
import org.educa.core.bean.RespuestasExamenBean;
import org.educa.core.dao.EvaluacionRepository;
import org.educa.core.entities.model.EstadoExamen;
import org.educa.core.entities.model.Evaluacion;
import org.educa.core.entities.model.ExamenUnidad;
import org.educa.core.services.EvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluacionServiceImpl implements EvaluacionService {

	@Autowired
	private EvaluacionRepository evaluacionRepository;
	
	@Override
	public Evaluacion evaluar(ExamenUnidad examen, RespuestasExamenBean respuestasBean) {

		Evaluacion evaluacion = null;
		Integer cantRespuestasCorrectas = 0;
		Integer cantRespuestasIncorrectas = 0;
		for (RespuestaExamenBean respuesta : respuestasBean.getRespuestas()) {
			if (examen.esCorrecta(respuesta)) {
				cantRespuestasCorrectas++;
			} else {
				cantRespuestasIncorrectas++;
			}
		}
		
		Date fechaCreacion = Calendar.getInstance().getTime(); 
		evaluacion = new Evaluacion();
		evaluacion.setIdUsuario(respuestasBean.getIdUsuario());
		evaluacion.setIdSesion(examen.getId().getNumero());
		evaluacion.setCantidadRespuestasCorrectas(cantRespuestasCorrectas);
		evaluacion.setCantidadRespuestasIncorrectas(cantRespuestasIncorrectas);
		
		
		if (examen.getCantPreguntasUsuario() != respuestasBean.getRespuestas().size()) {
			evaluacion.setEstado(EstadoExamen.INCOMPLETO);
		} else {
			double nota = (double)cantRespuestasCorrectas / (double)examen.getCantPreguntasUsuario();
			if (nota >= 0.6) {
				evaluacion.setEstado(EstadoExamen.APROBADO);
			} else {
				evaluacion.setEstado(EstadoExamen.DESAPROBADO);
			}
			
		}
		
		
		evaluacion.setFechaActualizacion(fechaCreacion);
		
		evaluacion = evaluacionRepository.save(evaluacion);
		
		return evaluacion;
	}

	@Override
	public List<Evaluacion> consultarEvaluacion(Integer numero, Long idUsuario) {
		List<Evaluacion> evaluaciones = evaluacionRepository.findByIdUsuarioAndIdSesion(idUsuario, numero);
		return evaluaciones;
	}

}
