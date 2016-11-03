package org.educa.core.dao;

import java.util.List;

import org.educa.core.entities.model.Evaluacion;
import org.springframework.data.repository.CrudRepository;

public interface EvaluacionRepository extends CrudRepository<Evaluacion, Long> {
	
	public List<Evaluacion> findByIdUsuarioAndIdSesionAndIdCursoAndNumeroUnidad(Integer idUsuario, Integer idSesion, Integer idCurso, Integer numeroUnidad);

}
