package org.educa.core.dao;

import org.educa.core.entities.model.Parametro;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametroRepository extends CrudRepository<Parametro, String> {

}
