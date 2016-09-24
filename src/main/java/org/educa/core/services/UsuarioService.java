package org.educa.core.services;

import org.educa.core.entities.model.Usuario;

public interface UsuarioService {
	
	void registrarUsuario(Usuario usuario);
	
	void activarUsuario(String claveActivacion, String idUsuario);

}
