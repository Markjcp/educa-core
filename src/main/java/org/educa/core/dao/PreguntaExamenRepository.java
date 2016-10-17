package org.educa.core.dao;

import org.educa.core.entities.model.PreguntaExamenUnidad;
import org.educa.core.entities.model.PreguntaExamenUnidadId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreguntaExamenRepository extends CrudRepository<PreguntaExamenUnidad, PreguntaExamenUnidadId>{

}
