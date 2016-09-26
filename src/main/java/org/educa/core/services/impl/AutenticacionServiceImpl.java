package org.educa.core.services.impl;

import java.util.List;

import org.educa.core.bean.UsuarioSeguridad;
import org.educa.core.dao.UsuarioRepository;
import org.educa.core.entities.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionServiceImpl implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<Usuario> usuarios = usuarioRepository.findByEmail(username);
		if(usuarios==null || usuarios.isEmpty() || usuarios.size()>1){
			throw new UsernameNotFoundException("No se encontró usuario con el nombre de usuario: " + username);
		}
		return new UsuarioSeguridad(usuarios.iterator().next());
	}

}
