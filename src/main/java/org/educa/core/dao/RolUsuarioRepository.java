package org.educa.core.dao;

import org.educa.core.entities.model.RolUsuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolUsuarioRepository extends CrudRepository<RolUsuario, Long> {

}
