package org.educa.core.security;

import java.util.List;

import org.educa.core.bean.UsuarioSeguridad;
import org.educa.core.dao.UsuarioRepository;
import org.educa.core.entities.model.Usuario;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionImplToken implements AuthenticationProvider {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
		String username = String.valueOf(auth.getPrincipal());
		String password = String.valueOf(auth.getCredentials());
		List<Usuario> usuarios = usuarioRepository.findByEmail(username);
		if(usuarios==null || usuarios.isEmpty() || usuarios.size()>1){
			throw new BadCredentialsException("No se encontró usuario con el nombre de usuario: " + username);
		}
		Usuario usuario = usuarios.iterator().next();
		BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
		if(!passwordEncryptor.checkPassword(password, usuario.getPassword())){
			throw new BadCredentialsException("Clave inválida para usuario: " + username);
		}
		usuario.setPassword("");
		UsuarioSeguridad usuarioSeguridad = new UsuarioSeguridad(usuario);
		return new UsernamePasswordAuthenticationToken(usuarioSeguridad,null,usuarioSeguridad.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return false;
	}

}
