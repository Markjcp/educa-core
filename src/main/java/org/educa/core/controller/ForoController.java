package org.educa.core.controller;

import java.util.Calendar;
import java.util.SortedSet;

import org.educa.core.controller.forms.ForoForm;
import org.educa.core.dao.SesionRepository;
import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.Sesion;
import org.educa.core.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("foro")
public class ForoController {
	
	private static final String LISTADO_SESION_FORO = "views/curso/foro/listado-sesiones-foro";	
	
	@Autowired
	private CursoService cursoService;
	
	@Autowired
	@Qualifier("sesionRepository")
	private SesionRepository sesionRepository;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String index(@PathVariable("id") long id, Model model) {
		Curso curso = this.cursoService.encontrarCursoPorId(id);
		ForoForm foroForm = new ForoForm();
		foroForm.setCurso(curso);
		
		SortedSet<Sesion> sesiones = sesionRepository.findByFechaAndIdCurso(Calendar.getInstance().getTime(), id);
		
		model.addAttribute("foroForm", foroForm);
		model.addAttribute("sesiones", sesiones);
		
		
		return LISTADO_SESION_FORO;
	}
	
	@RequestMapping(value = "/detalleForo/{idCurso}/{nroSesion}", method = RequestMethod.GET)
	public String detalleForo(@PathVariable("idCurso") long idCurso, @PathVariable("nroSesion") int nroSesion, Model model) {
		//TODO HAY Q HACERLO!
		Curso curso = this.cursoService.encontrarCursoPorId(idCurso);
		ForoForm foroForm = new ForoForm();
		foroForm.setCurso(curso);
		
		SortedSet<Sesion> sesiones = sesionRepository.findByFechaAndIdCurso(Calendar.getInstance().getTime(), idCurso);
		
		model.addAttribute("foroForm", foroForm);
		model.addAttribute("sesiones", sesiones);
		
		
		return LISTADO_SESION_FORO;
	}	
}
