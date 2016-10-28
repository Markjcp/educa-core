package org.educa.core.dao;

import java.util.List;

import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.SesionUsuarioId;

public interface UsuarioDao {
	
	public void persistirUsuarioFacebookSinValidaciones(String idFacebook, Long rolId, String nombreYApellido);
	
	public void persistirUsuarioGoogleSinValidaciones(String idGoogle, Long rolId, String nombreYApellido);
	
	public List<Curso> obtenerMisCursos(Long usuarioId);
	
	public List<SesionUsuarioId> obtenerMisSesiones(Long usuarioId);
	
	
}
