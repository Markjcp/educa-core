package org.educa.core.services;

import java.util.List;

import org.educa.core.entities.model.Curso;

public interface CursoService {

	void crearCurso(Curso curso);
	
	Curso encontrarCursoPorId(long id);

	List<Curso> obtenerTodos();
	
	void eliminarCurso(long id);
	
	void guardarCurso(Curso curso);
}
