package org.educa.core.services.impl;

import org.educa.core.dao.RolUsuarioRepository;
import org.educa.core.dao.UsuarioRepository;
import org.educa.core.entities.model.RolUsuario;
import org.educa.core.entities.model.Usuario;
import org.educa.core.exceptions.ActivacionException;
import org.educa.core.exceptions.ServiceException;
import org.educa.core.services.NotificacionService;
import org.educa.core.services.UsuarioService;
import org.educa.core.util.GeneradorClaveIdentificacion;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = ServiceException.class)
public class UsuarioServiceImpl implements UsuarioService{
	
	private static final long DOC_ROL_ID = 2L;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private NotificacionService notificacionService;
	
	@Autowired
	private RolUsuarioRepository rolUsuarioRepository;

	@Override
	public void registrarUsuario(Usuario usuario) {
		BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
		RolUsuario rol = rolUsuarioRepository.findOne(DOC_ROL_ID);
		usuario.setRol(rol);
		usuario.setPasswordConfirmacion(null);
		String cryptPassword = passwordEncryptor.encryptPassword(usuario.getPassword());
		usuario.setPassword(cryptPassword);
		usuario.setPasswordConfirmacion(cryptPassword);
		GeneradorClaveIdentificacion generador = new GeneradorClaveIdentificacion();
		usuario.setClaveActivacion(generador.proximoId());
		usuarioRepository.save(usuario);
		notificacionService.enviarActivacion(usuario);
		
	}

	@Override
	public void activarUsuario(String claveActivacion, String idUsuario) {
		Long id = null;
		try {
			id = Long.valueOf(idUsuario);
		} catch (Exception e) {
			throw new ActivacionException("No es un id valido de activacion");
		}
		Usuario usuario = usuarioRepository.findOne(id);
		if(usuario == null || !usuario.getClaveActivacion().equals(claveActivacion)){
			throw new ActivacionException("Las claves no coincide");
		}
		usuario.setActivado(true);
		usuario.setPasswordConfirmacion("-"); //@TODO sacar esto
		usuarioRepository.save(usuario);		
	}

}
