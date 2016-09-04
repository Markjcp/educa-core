package org.educa.core.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.educa.core.controller.forms.CursoForm;
import org.educa.core.dao.CategoryRepository;
import org.educa.core.dao.ProfesorRepository;
import org.educa.core.entities.model.Category;
import org.educa.core.entities.model.Profesor;
import org.educa.core.services.CursoService;
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
@RequestMapping("/cursoAdmin")
@SessionAttributes({ "cursoForm" })
//@Secured("ROLE_USER")  -- TODO [ediaz] VER ESTO XQ CAPAZ NOS SIRVE
public class CursoAdminController {

	private static final String ALTA_PROFESOR = "views/curso/alta-modificacion-admin";
	
	@Autowired private CursoService cursoService;
	
	@Autowired
	@Qualifier("categoryRepository")
	private CategoryRepository categoryRepository;
	
	@Autowired
	@Qualifier("profesorRepository")
	private ProfesorRepository profesorRepository;
	
	@RequestMapping(value = "/altaCurso", method = RequestMethod.GET)
	public String altaCurso(Model model){
		CursoForm cursoForm = new CursoForm();
		List<Category> categories = (List<Category>)categoryRepository.findAll();
		List<Profesor> profesores = (List<Profesor>)profesorRepository.findAll();
		
		model.addAttribute("cursoForm", cursoForm);
		model.addAttribute("categorias", categories);				
		model.addAttribute("profesores", profesores);
		
		return ALTA_PROFESOR;
	}

	@RequestMapping(value = "/altaCurso", method = RequestMethod.POST)
	public String guardarCurso(@ModelAttribute @Valid CursoForm cursoForm, BindingResult bindingResult, Model model){		
		if(bindingResult.hasFieldErrors("curso.*")){
			System.out.println("El curso tiene errores!!!!!!!!: " + cursoForm.getCurso());
			
			model.addAttribute("cursoForm", cursoForm);
			
			List<Category> categories = (List<Category>)categoryRepository.findAll();
			model.addAttribute("categorias", categories);
			
			List<Profesor> profesores = (List<Profesor>)profesorRepository.findAll();
			model.addAttribute("profesores", profesores);
			
			return ALTA_PROFESOR;
		}
		
		System.out.println("EL CURSO NO TIENE ERRORES Y SE VA A GUARDAR: " + cursoForm.getCurso());
		
		this.cursoService.crearCurso(cursoForm.getCurso());
		
		return "redirect:/index.html";
	}
}
