package org.educa.core.dao;

import org.educa.core.entities.model.SesionUsuario;
import org.educa.core.entities.model.SesionUsuarioId;
import org.springframework.data.repository.CrudRepository;

public interface SesionUsuarioRepository extends CrudRepository<SesionUsuario, SesionUsuarioId> {

}
