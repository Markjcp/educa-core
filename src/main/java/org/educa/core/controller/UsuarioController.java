package org.educa.core.controller;

import javax.validation.Valid;

import org.educa.core.controller.forms.UsuarioForm;
import org.educa.core.validator.RegistroUsuarioValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({ "usuarioForm" })
public class UsuarioController {
	
	private static final String REGISTRO_VIEW = "views/usuario/registro";
	private static final String LOGIN_VIEW = "views/usuario/login";
	
	@Autowired
	@Qualifier("registroUsuarioValidator")
	private RegistroUsuarioValidator registroUsuarioValidator;

	@RequestMapping(method = RequestMethod.GET)
	public String autenticacion(Model model) {
		model.addAttribute("usuarioForm", new UsuarioForm());
		return LOGIN_VIEW;
	}
	
	@RequestMapping(value = "/registro",method = RequestMethod.GET)
	public String registro(Model model) {
		model.addAttribute("usuarioForm", new UsuarioForm());
		return REGISTRO_VIEW;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute @Valid UsuarioForm cursoForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasFieldErrors("usuario.*")) {
			return LOGIN_VIEW;
		}
		System.out.println("Usuario: " + cursoForm.getUsuario().getEmail());
		return "";
	}
	
	@RequestMapping(value = "/registro", method = RequestMethod.POST)
	public String registro(@ModelAttribute @Valid UsuarioForm usuarioForm, BindingResult bindingResult, Model model) {
		registroUsuarioValidator.validate(usuarioForm.getUsuario(), bindingResult);
		if (bindingResult.hasFieldErrors("usuario.*")) {
			model.addAttribute("usuarioForm", usuarioForm);
			return REGISTRO_VIEW;
		}
		System.out.println("Usuario: " + usuarioForm.getUsuario().getEmail());
		return "";
	}
	

}
