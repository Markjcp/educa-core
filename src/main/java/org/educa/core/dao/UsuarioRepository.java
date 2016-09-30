package org.educa.core.dao;

import java.util.List;

import org.educa.core.entities.model.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
	
	public List<Usuario> findByEmail(String email);
	
	public List<Usuario> findByIdFacebook(String idFacebook);
	
	public List<Usuario> findByIdGoogle(String idGoogle);

}
