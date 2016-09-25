package org.educa.core.dao;

import java.util.Date;
import java.util.List;

import org.educa.core.entities.model.Curso;

public interface CursoDao extends GeneralDao<Curso> {
	
	List<Curso> obtenerProximosCursos(Date desde, Date hasta);

	List<Curso> findByLegajo(long legajo);

	List<Curso> findByLegajoAndNombreCurso(long legajo, String nombreCurso);

}
