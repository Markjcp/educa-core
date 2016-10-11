package org.educa.core.dao;

import org.educa.core.entities.model.ExamenUnidad;
import org.educa.core.entities.model.ExamenUnidadId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamenUnidadRepository extends CrudRepository<ExamenUnidad, ExamenUnidadId> {

}
