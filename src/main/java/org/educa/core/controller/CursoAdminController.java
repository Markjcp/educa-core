package org.educa.core.controller;

import java.util.List;

import javax.validation.Valid;

import org.educa.core.controller.forms.CursoForm;
import org.educa.core.dao.CategoriaRepository;
import org.educa.core.dao.DocenteRepository;
import org.educa.core.entities.model.Categoria;
import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.Docente;
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
	@Qualifier("categoriaRepository")
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	@Qualifier("docenteRepository")
	private DocenteRepository docenteRepository;
	
	@RequestMapping(value = "/altaCurso", method = RequestMethod.GET)
	public String altaCurso(Model model){
		CursoForm cursoForm = new CursoForm();
		List<Categoria> categories = (List<Categoria>)categoriaRepository.findAll();
		List<Docente> docentes = (List<Docente>)docenteRepository.findAll();
		
		model.addAttribute("cursoForm", cursoForm);
		model.addAttribute("categorias", categories);				
		model.addAttribute("docentes", docentes);
		
		return ALTA_PROFESOR;
	}

	@RequestMapping(value = "/altaCurso", method = RequestMethod.POST)
	public String guardarCurso(@ModelAttribute @Valid CursoForm cursoForm, BindingResult bindingResult, Model model){		
		if(bindingResult.hasFieldErrors("curso.*")){
			System.out.println("El curso tiene errores!!!!!!!!: " + cursoForm.getCurso());
			
			model.addAttribute("cursoForm", cursoForm);
			
			List<Categoria> categories = (List<Categoria>)categoriaRepository.findAll();
			model.addAttribute("categorias", categories);
			
			List<Docente> profesores = (List<Docente>)docenteRepository.findAll();
			model.addAttribute("profesores", profesores);
			
			return ALTA_PROFESOR;
		}
		
		System.out.println("EL CURSO NO TIENE ERRORES Y SE VA A GUARDAR: " + cursoForm.getCurso());
		Curso curso = cursoForm.getCurso();
		curso.setValoracionesPromedio(0);
		curso.setCantidadValoraciones(0);
		this.cursoService.crearCurso(curso);
		
		return "redirect:/index.html";
	}
}
