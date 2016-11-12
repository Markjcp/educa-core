package org.educa.core.services;

import java.util.List;

import org.educa.core.bean.RespuestasExamenBean;
import org.educa.core.entities.model.Evaluacion;
import org.educa.core.entities.model.ExamenUnidad;

public interface EvaluacionService {

	public Evaluacion evaluar(RespuestasExamenBean respuestasBean)  throws Exception ;

	public Evaluacion consultarEvaluacion(Integer idSesion, Integer idUsuario, Integer idCurso, Integer numeroUnidad)  throws Exception;
	
	public List<Evaluacion> consultarEvaluaciones(Integer numero, Integer idUsuario, Integer idCurso) throws Exception;
}
