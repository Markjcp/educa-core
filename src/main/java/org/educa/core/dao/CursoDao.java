package org.educa.core.dao;

import java.util.Date;
import java.util.List;

import org.educa.core.entities.model.Curso;

public interface CursoDao extends GeneralDao<Curso> {
	
	List<Curso> obtenerProximosCursos(Date desde, Date hasta);

}
