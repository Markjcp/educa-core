package org.educa.core.bean;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.educa.core.entities.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioSeguridad implements UserDetails {

	private static final long serialVersionUID = 162281455524339770L;

	private Usuario usuario;

	public UsuarioSeguridad(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Long getUsuarioId(){
		return this.usuario.getId();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		roles.add(new SimpleGrantedAuthority(usuario.getRol().getAcronimo()));		
		return roles;
	}

	@Override
	public String getPassword() {
		return usuario.getPassword();
	}

	@Override
	public String getUsername() {
		return usuario.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return usuario.getActivado() != null && usuario.getActivado();
	}

}
