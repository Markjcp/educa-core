package org.educa.core.validator;

import java.util.List;

import org.educa.core.dao.UsuarioRepository;
import org.educa.core.entities.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegistroUsuarioValidator implements Validator{
	
	@Autowired
	@Qualifier(value = "usuarioRepository")
	private UsuarioRepository usuarioRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Usuario.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Usuario usuario = (Usuario)target;
		if(usuario.getPassword()!=null && usuario.getPasswordConfirmacion()!=null && !usuario.getPassword().equals(usuario.getPasswordConfirmacion())){
			errors.rejectValue("usuario.passwordConfirmacion", null, "Los password no coinciden");
		}
		if(usuario.getEmail()!=null){
			List<Usuario> usuarios = usuarioRepository.findByEmail(usuario.getEmail());
			if(usuarios!= null && !usuarios.isEmpty()){
				errors.rejectValue("usuario.email", null, "Ya existe un usuario con el mail registrado");
			}
		}
	}

}
