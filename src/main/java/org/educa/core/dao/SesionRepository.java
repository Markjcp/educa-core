package org.educa.core.dao;

import org.educa.core.entities.model.ComponenteId;
import org.educa.core.entities.model.Sesion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SesionRepository extends CrudRepository<Sesion, ComponenteId> {

}
