package org.educa.core.services;

import java.util.List;

import org.educa.core.entities.model.Curso;

public interface CursoService {

	void crearCurso(Curso curso);
	
	Curso encontrarCursoPorId(long id);

	List<Curso> obtenerTodos();
	
	void eliminarCurso(long id);

	List<Curso> obtenerCursosDocente(long legajo);

	List<Curso> obtenerCursosDocente(long legajo, String nombreCurso);
	
	void guardarCurso(Curso curso);

}
