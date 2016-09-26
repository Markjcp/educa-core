package org.educa.core.dao;

import java.util.Date;
import java.util.List;

import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.Sesion;
import org.educa.core.entities.model.Unidad;

public interface CursoDao extends GeneralDao<Curso> {
	
	List<Curso> obtenerProximosCursos(Date desde, Date hasta);

	List<Curso> findByLegajo(long legajo);

	List<Curso> findByLegajoAndNombreCurso(long legajo, String nombreCurso);
	
	void deleteUnidad(Unidad unidad);
	
	void updateNumeroUnidad(Unidad unidad, int nuevoNumero);
	
	Curso findCursoByIdHidratado(long id) throws Exception;

	void deleteSesion(Sesion sesion);

	void updateNumeroSesion(Sesion sesion, int nuevoNumero);

	void deleteCurso(Curso curso);

}
