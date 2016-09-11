package org.educa.core.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/cursoAdmin")
@SessionAttributes({ "cursoForm" })
// @Secured("ROLE_USER") -- TODO [ediaz] VER ESTO XQ CAPAZ NOS SIRVE
public class CursoAdminController {

	private static final String FECHA_DEFAULT_KEY = "FECHA_DEFAUL_PROX_CURSO";

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private static final String ALTA_CURSO = "views/curso/alta-modificacion-admin";
	
	private static final String LISTADO_CURSOS = "views/curso/listado-admin";

	private static final Logger logger = Logger.getLogger(CursoAdminController.class.toString());

	@Autowired
	private CursoService cursoService;

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
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		cargaInicialListado(model, false);

		return LISTADO_CURSOS;
	}
	
	@RequestMapping(value = "/altaCurso", method = RequestMethod.GET)
	public String altaCurso(Model model) {
		cargaInicialAlta(model, null);

		return ALTA_CURSO;
	}

	@RequestMapping(value = "/altaCurso", method = RequestMethod.POST)
	public String guardarCurso(@ModelAttribute @Valid CursoForm cursoForm, BindingResult bindingResult, Model model) {
		Curso curso = cursoForm.getCurso();
		try {
			if (curso.getFoto() != null && curso.getFoto().getBytes().length != 0) {
				try {					
					curso.setImagen(curso.getFoto().getBytes());
				} catch (IOException e) {
					e.printStackTrace();
					bindingResult.rejectValue("curso.foto", "ERROR-FOTO", "Error al obtener la imagen seleccionada.");
				}
			} else {
				//Si no viene de editar, no deberia de venir sin imagen. En editar, si no cambio la imagen, este campo viene sin bytes.
				if(!cursoForm.isEditar()){
					bindingResult.rejectValue("curso.foto", "ERROR-FOTO", "Debe de seleccionar una imagen.");
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if(!cursoForm.isEditar()){
			nombreRepetidoValidator.validate(cursoForm.getCurso(),bindingResult);
		}
		
		if (bindingResult.hasFieldErrors("curso.*")) {
			boolean eraEdicion = cursoForm.isEditar();
			cargaInicialAlta(model, cursoForm);
			if(eraEdicion){
				model.addAttribute("edicion", true);
			}			
			return ALTA_CURSO;
		}

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
		
		cargaInicialListado(model, true);

		return LISTADO_CURSOS;
	}

	@RequestMapping(value = "/altaCurso/{id}", method = RequestMethod.GET)
	public String altaCurso(@PathVariable("id") long id, Model model) {
		Curso curso = this.cursoService.encontrarCursoPorId(id);		
		CursoForm cursoForm = new CursoForm();
		cursoForm.setCurso(curso);
		cursoForm.setEditar(true);
		
		cargaInicialAlta(model, cursoForm);
		model.addAttribute("edicion", true);
		
		//data:image/jpg;base64,${foto en base 64}
		String foto = "data:image/jpg;base64,";
		String base64 = Base64.encodeBase64String(curso.getImagen());
		foto +=base64;		
		model.addAttribute("foto", foto);
		
		return ALTA_CURSO;
	}

	private void cargaInicialAlta(Model model, CursoForm cursoForm) {
		if (cursoForm == null) {
			cursoForm = new CursoForm();
		}

		List<Categoria> categories = (List<Categoria>) categoriaRepository.findAll();
		List<Docente> docentes = (List<Docente>) docenteRepository.findAll();

		model.addAttribute("cursoForm", cursoForm);
		model.addAttribute("categorias", categories);
		model.addAttribute("docentes", docentes);
		model.addAttribute("edicion", false);
	}
	
	private void cargaInicialListado(Model model, boolean vieneDeAlta) {
		List<Curso> cursos = cursoService.obtenerTodos();
		model.addAttribute("cursos", cursos);		
		model.addAttribute("mostrarMensajeAlta", false);
		
		if(vieneDeAlta) {
			model.addAttribute("mostrarMensajeAlta", true);
		}
	}
}
