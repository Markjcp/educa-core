package org.educa.core.dao;

import java.util.List;

import org.educa.core.entities.model.VideoUnidad;
import org.educa.core.entities.model.VideoUnidadId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoUnidadRepository extends CrudRepository<VideoUnidad, VideoUnidadId> {
	
	@Query(" select u from VideoUnidad u where u.id.numero = :numero and u.id.idCurso = :idCurso  ")
	List<VideoUnidad> findByNumeroAndIdCurso(@Param("numero") Integer numero, @Param("idCurso") Long idCurso);
}
