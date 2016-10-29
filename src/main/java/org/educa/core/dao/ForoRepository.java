package org.educa.core.dao;

import org.educa.core.entities.model.Foro;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForoRepository extends CrudRepository<Foro, Long>  {

}
