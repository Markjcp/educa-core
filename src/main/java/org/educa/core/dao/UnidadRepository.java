package org.educa.core.dao;

import org.educa.core.entities.model.Unidad;
import org.educa.core.entities.model.UnidadId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadRepository extends CrudRepository<Unidad, UnidadId> {

}
