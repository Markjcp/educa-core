package org.educa.core.controller;

import org.educa.core.controller.forms.CursoForm;
import org.educa.core.entities.model.Curso;
import org.educa.core.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/cursoNoAdmin")
@SessionAttributes({ "cursoForm" })
// @Secured("ROLE_USER") -- TODO [ediaz] VER ESTO XQ CAPAZ NOS SIRVE
public class CursoNoAdminController {

	private static final String LISTADO_CURSO = "views/curso/listado-no-admin";
	private static final String CONFIGURACION_CURSO = "views/curso/configuracion-curso-no-admin";
	
	@Autowired
	private CursoService cursoService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		
		return LISTADO_CURSO;
	}
	
	@RequestMapping(value = "/configuracionCurso/{id}", method = RequestMethod.GET)
	public String configuracionCurso(@PathVariable("id") long id, Model model) {
		Curso curso = this.cursoService.encontrarCursoPorId(id);
		CursoForm cursoForm = new CursoForm();
		cursoForm.setCurso(curso);
		
		model.addAttribute("cursoForm", cursoForm);
		
		return CONFIGURACION_CURSO;
	}
}
