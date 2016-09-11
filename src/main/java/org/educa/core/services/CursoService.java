package org.educa.core.services;

import org.educa.core.entities.model.Curso;

public interface CursoService {

	void crearCurso(Curso curso);
	
	Curso encontrarCursoPorId(long id);
}
