package org.educa.core.services;

import java.util.List;

import org.educa.core.bean.RespuestasExamenBean;
import org.educa.core.entities.model.Evaluacion;
import org.educa.core.entities.model.ExamenUnidad;

public interface EvaluacionService {

	public Evaluacion evaluar(ExamenUnidad examen, RespuestasExamenBean respuestasBean);

	public List<Evaluacion> consultarEvaluacion(Integer numero, Long idUsuario);
}
