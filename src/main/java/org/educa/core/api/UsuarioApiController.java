package org.educa.core.api;

import java.util.ArrayList;
import java.util.List;

import org.educa.core.bean.FacebookLoginBean;
import org.educa.core.bean.GoogleLoginBean;
import org.educa.core.bean.ResponseBean;
import org.educa.core.dao.RolUsuarioRepository;
import org.educa.core.dao.SesionUsuarioRepository;
import org.educa.core.dao.UsuarioDao;
import org.educa.core.dao.UsuarioRepository;
import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.RolUsuario;
import org.educa.core.entities.model.SesionUsuario;
import org.educa.core.entities.model.SesionUsuarioId;
import org.educa.core.entities.model.Usuario;
import org.educa.core.exceptions.MasDeUnUsuarioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioApiController {
	
	private static final long USUARIO_ROL = 3L;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RolUsuarioRepository rolUsuarioRepository;
	
	@Autowired
	private SesionUsuarioRepository sesionUsuarioRepository;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@RequestMapping(method = RequestMethod.POST, value = "/login/facebook")
	public Usuario loginFacebook(@RequestBody FacebookLoginBean facebookLoginBean){
		List<Usuario> usuarios = usuarioRepository.findByIdFacebook(facebookLoginBean.getToken());
		Usuario resultado = buildUsuario(usuarios);
		if(resultado.getId()==null){
			usuarioDao.persistirUsuarioFacebookSinValidaciones(facebookLoginBean.getToken(), USUARIO_ROL);
			resultado = usuarioRepository.findByIdFacebook(facebookLoginBean.getToken()).iterator().next();
		}		
		return resultado;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "login/google")
	public Usuario loginFacebook(@RequestBody GoogleLoginBean googleLoginBean){
		List<Usuario> usuarios = usuarioRepository.findByIdGoogle(googleLoginBean.getToken());
		Usuario resultado = buildUsuario(usuarios);
		if(resultado.getId()==null){
			usuarioDao.persistirUsuarioGoogleSinValidaciones(googleLoginBean.getToken(), USUARIO_ROL);
			resultado = usuarioRepository.findByIdGoogle(googleLoginBean.getToken()).iterator().next();
		}
		return resultado;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "suscribir/{idUsuario}/{idCurso}/{numeroSesion}")
	public ResponseEntity<ResponseBean> suscribirse(@PathVariable Long idUsuario, @PathVariable Long idCurso, @PathVariable Integer numeroSesion ){
		SesionUsuarioId id = new SesionUsuarioId();
		id.setId(idUsuario);
		id.setIdCurso(idCurso);
		id.setNumero(numeroSesion);
		SesionUsuario auxiliar = sesionUsuarioRepository.findOne(id);
		if(auxiliar!=null){
			return new ResponseEntity<ResponseBean>(new ResponseBean(false),HttpStatus.ALREADY_REPORTED);
		} 
		auxiliar = new SesionUsuario();
		auxiliar.setId(id);
		sesionUsuarioRepository.save(auxiliar);
		return new ResponseEntity<ResponseBean>(new ResponseBean(true),HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "desuscribir/{idUsuario}/{idCurso}/{numeroSesion}")
	public ResponseEntity<ResponseBean> desuscribirse(@PathVariable Long idUsuario, @PathVariable Long idCurso, @PathVariable Integer numeroSesion ){
		SesionUsuarioId id = new SesionUsuarioId();
		id.setId(idUsuario);
		id.setIdCurso(idCurso);
		id.setNumero(numeroSesion);
		SesionUsuario auxiliar = sesionUsuarioRepository.findOne(id);
		if(auxiliar==null){
			return new ResponseEntity<ResponseBean>(new ResponseBean(false),HttpStatus.NOT_FOUND);
		}
		auxiliar = new SesionUsuario();
		auxiliar.setId(id);
		sesionUsuarioRepository.delete(auxiliar);
		return new ResponseEntity<ResponseBean>(new ResponseBean(true),HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "mis-cursos/{idUsuario}")
	public ResponseEntity<List<Curso>> misCursos(@PathVariable Long idUsuario){
		List<Curso> resultado = new ArrayList<Curso>();
		try {
			resultado = usuarioDao.obtenerMisCursos(idUsuario);
			return new ResponseEntity<List<Curso>>(resultado,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Curso>>(resultado,HttpStatus.NOT_FOUND);
		}		
	}
	
	
	
	private Usuario buildUsuario(List<Usuario> usuarios){
		if(usuarios!=null && usuarios.size()>1){
			throw new MasDeUnUsuarioException();
		}
		if(usuarios!=null && usuarios.size()==1){
			return usuarios.iterator().next();
		}
		RolUsuario rol = rolUsuarioRepository.findOne(USUARIO_ROL);
		Usuario usuarioNuevo = new Usuario();
		usuarioNuevo.setActivado(true);
		usuarioNuevo.setRol(rol);
		return usuarioNuevo;
	}


}
