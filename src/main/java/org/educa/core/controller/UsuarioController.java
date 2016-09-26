package org.educa.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.educa.core.bean.UsuarioSeguridad;
import org.educa.core.controller.forms.UsuarioForm;
import org.educa.core.dao.DocenteRepository;
import org.educa.core.dao.ParametroRepository;
import org.educa.core.entities.model.Docente;
import org.educa.core.entities.model.Parametro;
import org.educa.core.entities.model.Usuario;
import org.educa.core.exceptions.MailException;
import org.educa.core.security.AutenticacionImplToken;
import org.educa.core.security.VerifyRecaptcha;
import org.educa.core.services.UsuarioService;
import org.educa.core.validator.RegistroUsuarioValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({ "usuarioForm" })
@RequestMapping
public class UsuarioController {
	
	private static final String RECAPTCHA_SECRET = "RECAPTCHA_SECRET";
	private static final String REGISTRO_VIEW = "views/usuario/registro";
	private static final String LOGIN_VIEW = "views/usuario/login";
	
	@Autowired
	private AutenticacionImplToken token;
	
	@Autowired
	@Qualifier("registroUsuarioValidator")
	private RegistroUsuarioValidator registroUsuarioValidator;
	
	@Autowired
	private DocenteRepository docenteRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ParametroRepository parametroRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public String autenticacion(Model model) {
		model.addAttribute("usuarioForm", new UsuarioForm());
		return LOGIN_VIEW;
	}
	
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("usuarioForm", new UsuarioForm());
		return LOGIN_VIEW;
	}
	
	@RequestMapping(value = "/registro",method = RequestMethod.GET)
	public String registro(Model model) {
		model.addAttribute("usuarioForm", new UsuarioForm());
		return REGISTRO_VIEW;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute @Valid UsuarioForm usuarioForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasFieldErrors("usuario.email") || bindingResult.hasFieldErrors("usuario.password")) {
			return LOGIN_VIEW;
		}
		Usuario usuario = usuarioForm.getUsuario();
		Authentication auth = null;
		try {
			auth = token.authenticate(new UsernamePasswordAuthenticationToken(usuario.getEmail(),usuario.getPassword(),null));			
			SecurityContextHolder.getContext().setAuthentication(auth);
		} catch (BadCredentialsException e) {
			model.addAttribute("errorLogin", true);
			return LOGIN_VIEW;
		}
		UsuarioSeguridad usuarioSeguridad = (UsuarioSeguridad)auth.getPrincipal();
		if(!usuarioSeguridad.isEnabled()){
			model.addAttribute("errorActivo", true);
			return LOGIN_VIEW;
		}
		if(auth.getAuthorities().contains(new SimpleGrantedAuthority("ROL_ADMIN"))){
			return "redirect:/cursoAdmin";			
		}else{
			List<Docente> docentes = docenteRepository.findByIdUsuario(usuarioSeguridad.getUsuarioId());
			if(docentes==null || docentes.isEmpty() || docentes.size()>1){
				model.addAttribute("errorLogin", true);
				return LOGIN_VIEW;	
			}			
			return "redirect:/docente/"+docentes.iterator().next().getId() +"/bandeja-cursos";
		}
	}
	
	@RequestMapping(value = "/registro", method = RequestMethod.POST)
	public String registro(@ModelAttribute @Valid UsuarioForm usuarioForm, BindingResult bindingResult, Model model, @RequestParam("g-recaptcha-response") String recaptcha) {
		registroUsuarioValidator.validate(usuarioForm.getUsuario(), bindingResult);
		if (bindingResult.hasFieldErrors("usuario.*")) {
			model.addAttribute("usuarioForm", usuarioForm);
			return REGISTRO_VIEW;
		}
		Parametro parametro = parametroRepository.findOne(RECAPTCHA_SECRET);
		if(parametro==null || !VerifyRecaptcha.verify(recaptcha,parametro.getValor())){
			model.addAttribute("usuarioForm", usuarioForm);
			model.addAttribute("errorCaptcha", true);
			return REGISTRO_VIEW;
		}

		try {
			usuarioService.registrarUsuario(usuarioForm.getUsuario());			
		} catch (MailException e) {
			model.addAttribute("usuarioForm", usuarioForm);
			model.addAttribute("errorMail", true);			
			return REGISTRO_VIEW;
		}
		model.addAttribute("mostrarMensajeMailActivacion", true);
		return LOGIN_VIEW;
	}
	
	@RequestMapping(value="/activar-cuenta",method = RequestMethod.GET)
	public String activar(Model model,@RequestParam("clave-activacion") String claveActivacion, @RequestParam("id-usuario") String idUsuario){
		try {
			usuarioService.activarUsuario(claveActivacion, idUsuario);			
		} catch (Exception e) {
			model.addAttribute("errorActivacion", true);
			model.addAttribute("usuarioForm", new UsuarioForm());
			return LOGIN_VIEW;
		}
		model.addAttribute("usuarioForm", new UsuarioForm());
		model.addAttribute("mostrarMensajeActivado", true);		
		return LOGIN_VIEW;
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
	}
	

}
