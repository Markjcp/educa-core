package org.educa.core.dao;

import java.util.Date;
import java.util.List;

import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.ExamenUnidad;
import org.educa.core.entities.model.MaterialUnidad;
import org.educa.core.entities.model.Sesion;
import org.educa.core.entities.model.Unidad;
import org.educa.core.entities.model.VideoUnidad;

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
	
	List<ExamenUnidad> findExamenUnidadByNumeroAndIdCurso(Integer numero, Long idCurso);
	
	List<MaterialUnidad> findMaterialUnidadByNumeroAndIdCurso(Integer numero, Long idCurso);
	
	List<VideoUnidad> findVideoUnidadByNumeroAndIdCurso(Integer numero, Long idCurso);

	boolean sesionTieneAlumnosInscriptos(long idCurso, int numeroSesion);

	boolean unidadFueRendidad(long idCurso, int numeroUnidad);
	
	boolean cursoTieneAlumnosInscriptos(long idCurso);
}
