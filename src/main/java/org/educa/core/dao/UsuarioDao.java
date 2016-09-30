package org.educa.core.dao;

import java.util.List;

import org.educa.core.entities.model.Curso;

public interface UsuarioDao {
	
	public void persistirUsuarioFacebookSinValidaciones(String idFacebook, Long rolId);
	
	public void persistirUsuarioGoogleSinValidaciones(String idGoogle, Long rolId);
	
	public List<Curso> obtenerMisCursos(Long usuarioId);
	
	
}
