package org.educa.core.services;

import java.util.List;

import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.Sesion;
import org.educa.core.entities.model.Unidad;

public interface CursoService {

	void crearCurso(Curso curso);
	
	Curso encontrarCursoPorId(long id);

	List<Curso> obtenerTodos();
	
	void eliminarCurso(long id);

	List<Curso> obtenerCursosDocente(long legajo);

	List<Curso> obtenerCursosDocente(long legajo, String nombreCurso);
	
	void guardarCurso(Curso curso);

	void crearUnidad(Curso curso, Unidad unidadNueva);

	boolean eliminarUnidadCurso(Curso curso, long idUnidad, int numeroUnidad);

	void crearSesion(Curso curso, Sesion sesionNueva);

	boolean eliminarSesionCurso(Curso curso, long idSesion, int numeroSesion);
}
