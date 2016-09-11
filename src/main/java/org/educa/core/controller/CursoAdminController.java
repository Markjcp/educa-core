package org.educa.core.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.educa.core.controller.forms.CursoForm;
import org.educa.core.dao.CategoriaRepository;
import org.educa.core.dao.DocenteRepository;
import org.educa.core.dao.ParametroRepository;
import org.educa.core.entities.model.Categoria;
import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.Docente;
import org.educa.core.entities.model.Parametro;
import org.educa.core.services.CursoService;
import org.educa.core.validator.NombreRepetidoValidator;
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
	
	private static final String FECHA_DEFAULT_KEY = "FECHA_DEFAUL_PROX_CURSO";
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private static final String ALTA_PROFESOR = "views/curso/alta-modificacion-admin";
	
	private static final Logger logger = Logger.getLogger(CursoAdminController.class.toString());
	
	@Autowired private CursoService cursoService;
	
	@Autowired
	@Qualifier("categoriaRepository")
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	@Qualifier("docenteRepository")
	private DocenteRepository docenteRepository;
	
	@Autowired
	@Qualifier("parametroRepository")
	private ParametroRepository parametroRepository;
	
	@Autowired
	@Qualifier("nombreRepetidoValidator")
	private NombreRepetidoValidator nombreRepetidoValidator;
	
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
		nombreRepetidoValidator.validate(cursoForm.getCurso(),bindingResult);
		if(bindingResult.hasFieldErrors("curso.*")){
			System.out.println("El curso tiene errores!!!!!!!!: " + cursoForm.getCurso());
			
			model.addAttribute("cursoForm", cursoForm);
			
			List<Categoria> categories = (List<Categoria>)categoriaRepository.findAll();
			model.addAttribute("categorias", categories);
			
			List<Docente> docentes = (List<Docente>)docenteRepository.findAll();
			model.addAttribute("docentes", docentes);
			
			return ALTA_PROFESOR;
		}
		
		System.out.println("EL CURSO NO TIENE ERRORES Y SE VA A GUARDAR: " + cursoForm.getCurso());
		Curso curso = cursoForm.getCurso();
		Parametro fechaDefaultParametro = parametroRepository.findOne(FECHA_DEFAULT_KEY);
		Date fechaDefault;
		try {
			fechaDefault = sdf.parse(fechaDefaultParametro.getValor());
			curso.setFechaEstimadaProximaSesion(fechaDefault);
		} catch (ParseException e) {
			logger.severe("No se pudo parsear el parametro " + e.getMessage());			
		}
		curso.setValoracionesPromedio(0);
		curso.setCantidadValoraciones(0);
		this.cursoService.crearCurso(curso);
		
		return "redirect:/index.html";
	}
}
