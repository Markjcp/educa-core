package org.educa.core.dao;

import java.util.List;

import org.educa.core.entities.model.VideoUnidad;
import org.educa.core.entities.model.VideoUnidadId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoUnidadRepository extends CrudRepository<VideoUnidad, VideoUnidadId> {
	
	public List<VideoUnidad> findByNumeroAndIdCurso(Integer numero, Long idCurso);

}
