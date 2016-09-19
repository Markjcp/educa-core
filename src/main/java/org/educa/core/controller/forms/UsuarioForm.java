package org.educa.core.controller.forms;

import javax.validation.Valid;

import org.educa.core.entities.model.Usuario;

public class UsuarioForm {

	@Valid
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
