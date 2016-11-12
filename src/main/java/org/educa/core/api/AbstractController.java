package org.educa.core.api;

import javax.servlet.http.HttpServletRequest;

import org.educa.core.bean.UsuarioSeguridad;
import org.educa.core.entities.model.Usuario;
import org.springframework.security.core.context.SecurityContext;

public abstract class AbstractController {
	
	public Usuario obtenerUsuarioLogueado(HttpServletRequest request){
		SecurityContext contexto = (SecurityContext)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		Usuario usuarioLogeado = null;
		if(contexto != null){
			usuarioLogeado = contexto.getAuthentication() == null ? null : (((UsuarioSeguridad)contexto.getAuthentication().getPrincipal()).getUsuario());
		}
		
		return usuarioLogeado;
	}

}
