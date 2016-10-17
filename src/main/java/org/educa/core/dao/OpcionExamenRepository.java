package org.educa.core.dao;

import org.educa.core.entities.model.OpcionExamenUnidad;
import org.educa.core.entities.model.OpcionExamenUnidadId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpcionExamenRepository extends CrudRepository<OpcionExamenUnidad, OpcionExamenUnidadId>{

}
