package org.educa.core.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.educa.core.controller.forms.CursoForm;
import org.educa.core.dao.SesionRepository;
import org.educa.core.dao.UnidadRepository;
import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.Sesion;
import org.educa.core.entities.model.Unidad;
import org.educa.core.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/cursoNoAdmin")
@SessionAttributes({ "cursoForm" })
// @Secured("ROLE_USER") -- TODO [ediaz] VER ESTO XQ CAPAZ NOS SIRVE
public class CursoNoAdminController {
	
	private static final String CONFIGURACION_CURSO = "views/curso/configuracion-curso-no-admin";
	
	@Autowired
	private CursoService cursoService;
	
	@Autowired
	@Qualifier("unidadRepository")
	private UnidadRepository unidadRepository;
	
	@Autowired
	@Qualifier("sesionRepository")
	private SesionRepository sesionRepository;
	
	@RequestMapping(value = "/configuracionCurso/{id}", method = RequestMethod.GET)
	public String configuracionCurso(@PathVariable("id") long id, Model model) {
		Curso curso = this.cursoService.encontrarCursoPorId(id);
		
		cargarValoresBasicosParaUnidad(model, false, false, false, false);
		cargarValoresBasicosParaSesion(curso, model, false, false, false, false);
		
		CursoForm cursoForm = new CursoForm();
		cursoForm.setCurso(curso);
		
		if(curso == null || curso.getUnidades() == null || curso.getUnidades().isEmpty()){
			model.addAttribute("mostrarMensajeErrorCrearSesionSinUnidad", true);			
		}
		
		model.addAttribute("cursoForm", cursoForm);
		
		return CONFIGURACION_CURSO;
	}
	
	@RequestMapping(value = "/configuracionUnidadCurso", method = RequestMethod.POST)
	public String guardarUnidadCurso(@ModelAttribute @Valid CursoForm cursoForm, BindingResult bindingResult, Model model) {
		Curso curso = cursoForm.getCurso();
		//if (bindingResult.hasErrors()) {
		if (bindingResult.hasFieldErrors("nuevaUnidad.*")) {		
			model.addAttribute("cursoForm", cursoForm);
			cargarValoresBasicosParaUnidad(model, false, false, false, false);
			cargarValoresBasicosParaSesion(curso, model, false, false, false, false);
			
			return CONFIGURACION_CURSO;
		}
		
		//Determino si se creo una nueva unidad
		Unidad unidadNueva = cursoForm.getNuevaUnidad();		
		if(unidadNueva != null){			
			this.cursoService.crearUnidad(curso, unidadNueva);			
			cargarValoresBasicosParaUnidad(model, true, false, false, false);			
		}
		
		Curso cursoHidratado = this.cursoService.encontrarCursoPorId(curso.getId());
		//Seteo los nuevos valores
		cursoForm = new CursoForm();
		cursoForm.setCurso(cursoHidratado);
		
		model.addAttribute("cursoForm", cursoForm);
		cargarValoresBasicosParaSesion(cursoHidratado, model, false, false, false, false);
		
		return CONFIGURACION_CURSO;
	}
	
	@RequestMapping(value = "/actualizarUnidad/{idCurso}", method = RequestMethod.POST)
	public String actualizarUnidad(@PathVariable("idCurso") long idCurso, @ModelAttribute("unidad") @Valid Unidad unidad, BindingResult bindingResult, Model model) {		
		Curso curso = this.cursoService.encontrarCursoPorId(idCurso);
		CursoForm cursoForm = new CursoForm();
		cursoForm.setCurso(curso);
		
		if (bindingResult.hasErrors()) {			
			cargarValoresBasicosParaUnidad(model, false, false, true, false);
			cargarValoresBasicosParaSesion(curso, model, false, false, false, false);			
			model.addAttribute("cursoForm", cursoForm);
			
			List<String> errores = new ArrayList<String>();
			for(ObjectError error : bindingResult.getAllErrors()){
				errores.add(error.getDefaultMessage());				
			}
			
			model.addAttribute("descripcionUnidadModificada", unidad.getDescripcionLarga());
			model.addAttribute("erroresActualizacionUnidad", errores);
			
			return CONFIGURACION_CURSO;
		}
		
		unidad.setCurso(curso);
		this.unidadRepository.save(unidad);
		Curso cursoHidratado = this.cursoService.encontrarCursoPorId(curso.getId());		
		cursoForm.setCurso(cursoHidratado);
		
		model.addAttribute("cursoForm", cursoForm);
		
		cargarValoresBasicosParaUnidad(model, false, true, false, false);
		cargarValoresBasicosParaSesion(cursoHidratado, model, false, false, false, false);
		
		return CONFIGURACION_CURSO;		
	}
	
	@RequestMapping(value = "/eliminarUnidad/{idCurso}/{idUnidad}/{numeroUnidad}", method = RequestMethod.GET)
	public String eliminarUnidad(@PathVariable("idCurso") long idCurso, @PathVariable("idUnidad") long idUnidad, @PathVariable("numeroUnidad") int numeroUnidad, Model model) {
		Curso curso = this.cursoService.encontrarCursoPorId(idCurso);
		boolean eliminada = false;
		if(curso != null && curso.getUnidades() != null){
			eliminada = this.cursoService.eliminarUnidadCurso(curso, idUnidad, numeroUnidad);			
		}
		
		Curso cursoHidratado = this.cursoService.encontrarCursoPorIdHidratado(idCurso);		
		//Seteo los nuevos valores		
		CursoForm cursoForm = new CursoForm();
		cursoForm.setCurso(cursoHidratado);
		
		model.addAttribute("cursoForm", cursoForm);
		
		cargarValoresBasicosParaUnidad(model, false, false, false, eliminada);
		cargarValoresBasicosParaSesion(cursoHidratado, model, false, false, false, false);
				
		return CONFIGURACION_CURSO;
	}
	
	@RequestMapping(value = "/configuracionSesionCurso", method = RequestMethod.POST)
	public String guardarSesionCurso(@ModelAttribute @Valid CursoForm cursoForm, BindingResult bindingResult, Model model) {
		Curso curso = cursoForm.getCurso();
		//if (bindingResult.hasErrors()) {
		if (bindingResult.hasFieldErrors("nuevaSesion.*")) {
			model.addAttribute("cursoForm", cursoForm);
			cargarValoresBasicosParaUnidad(model, false, false, false, false);
			cargarValoresBasicosParaSesion(curso, model, false, false, false, false);
			
			return CONFIGURACION_CURSO;
		}
		
		Curso cursoHidratado = curso; //Seteo un valor por defecto
		//Determino si se creo una nueva sesion
		Sesion sesionNueva = cursoForm.getNuevaSesion();	
		if(sesionNueva != null){
			//Validar fechas
			boolean valido = validarFechasSesion(sesionNueva, bindingResult);
			if(!valido){
				model.addAttribute("cursoForm", cursoForm);
				cargarValoresBasicosParaUnidad(model, false, false, false, false);
				cargarValoresBasicosParaSesion(curso, model, false, false, false, false);
				
				return CONFIGURACION_CURSO;
			}
			
			//Calendar calendar = 
			
			this.cursoService.crearSesion(curso, sesionNueva);
			
			cursoHidratado = this.cursoService.encontrarCursoPorId(curso.getId());
			cargarValoresBasicosParaSesion(cursoHidratado, model, true, false, false, false);
		}
				
		//Seteo los nuevos valores
		cursoForm = new CursoForm();
		cursoForm.setCurso(cursoHidratado);
		
		model.addAttribute("cursoForm", cursoForm);		
		cargarValoresBasicosParaUnidad(model, false, false, false, false);		
		
		return CONFIGURACION_CURSO;
	}
	
	@RequestMapping(value = "/actualizarSesion/{idCurso}", method = RequestMethod.POST)
	public String actualizarSesion(@PathVariable("idCurso") long idCurso, @ModelAttribute("sesion") @Valid Sesion sesion, BindingResult bindingResult, Model model) {		
		Curso curso = this.cursoService.encontrarCursoPorId(idCurso);
		CursoForm cursoForm = new CursoForm();
		cursoForm.setCurso(curso);
		
		if (bindingResult.hasErrors()) {			
			cargarValoresBasicosParaUnidad(model, false, false, false, false);
			cargarValoresBasicosParaSesion(curso, model, false, false, true, false);			
			model.addAttribute("cursoForm", cursoForm);
			
			List<String> errores = new ArrayList<String>();
			for(ObjectError error : bindingResult.getAllErrors()){
				errores.add(error.getDefaultMessage());				
			}
			
			model.addAttribute("descripcionSesionModificada", sesion.getDescripcionLarga());
			model.addAttribute("erroresActualizacionSesion", errores);
			
			return CONFIGURACION_CURSO;
		}
		
		sesion.setCurso(curso);
		this.sesionRepository.save(sesion);
		Curso cursoHidratado = this.cursoService.encontrarCursoPorId(curso.getId());		
		cursoForm.setCurso(cursoHidratado);
		
		model.addAttribute("cursoForm", cursoForm);
		
		cargarValoresBasicosParaUnidad(model, false, false, false, false);
		cargarValoresBasicosParaSesion(cursoHidratado, model, false, true, false, false);
		
		return CONFIGURACION_CURSO;		
	}
	
	@RequestMapping(value = "/eliminarSesion/{idCurso}/{idSesion}/{numeroSesion}", method = RequestMethod.GET)
	public String eliminarSesion(@PathVariable("idCurso") long idCurso, @PathVariable("idSesion") long idSesion, @PathVariable("numeroSesion") int numeroSesion, Model model) {
		Curso curso = this.cursoService.encontrarCursoPorId(idCurso);
		boolean eliminada = false;
		if(curso != null && curso.getUnidades() != null){
			eliminada = this.cursoService.eliminarSesionCurso(curso, idSesion, numeroSesion);			
		}
		
		Curso cursoHidratado = this.cursoService.encontrarCursoPorIdHidratado(idCurso);		
		//Seteo los nuevos valores		
		CursoForm cursoForm = new CursoForm();
		cursoForm.setCurso(cursoHidratado);
		
		model.addAttribute("cursoForm", cursoForm);
		
		cargarValoresBasicosParaUnidad(model, false, false, false, false);
		cargarValoresBasicosParaSesion(cursoHidratado, model, false, false, false, eliminada);
				
		return CONFIGURACION_CURSO;
	}
	
	private void cargarValoresBasicosParaUnidad(Model model, boolean alta, boolean actualizacion, boolean actualizacionError, boolean elimacion){
		model.addAttribute("unidad", new Unidad());
		model.addAttribute("mostrarMensajeAltaUnidad", alta);
		model.addAttribute("mostrarMensajeErrorActualizacionUnidad", actualizacionError);
		model.addAttribute("mostrarMensajeActualizacionUnidad", actualizacion);
		model.addAttribute("mostrarMensajeEliminarUnidad", elimacion);		
	}
	
	private void cargarValoresBasicosParaSesion(Curso curso, Model model, boolean alta, boolean actualizacion, boolean actualizacionError, boolean elimacion){
		if(curso == null || curso.getUnidades() == null || curso.getUnidades().isEmpty()){
			model.addAttribute("mostrarMensajeErrorCrearSesionSinUnidad", true);			
		}
		
		model.addAttribute("sesion", new Sesion());
		model.addAttribute("mostrarMensajeAltaSesion", alta);
		model.addAttribute("mostrarMensajeActualizacionSesion", actualizacion);
		model.addAttribute("mostrarMensajeErrorActualizacionSesion", actualizacionError);
		model.addAttribute("mostrarMensajeEliminarSesion", elimacion);		
	}
	
	private boolean validarFechasSesion(Sesion sesionNueva, BindingResult bindingResult) {
		// TODO Auto-generated method stub
		boolean valida = true;
		if(sesionNueva.getFechaDesdeInscripcion().compareTo(sesionNueva.getFechaDesde()) > 0 
				|| sesionNueva.getFechaDesdeInscripcion().compareTo(sesionNueva.getFechaHasta()) > 0){
			//Deberia de ser menor
			bindingResult.resolveMessageCodes("La fecha de inscripcion debe de ser menor al resto de las fechas.", "nuevaSesion.fechaDesdeInscripcion");
			valida = false;
		}
		
		if(sesionNueva.getFechaDesde().compareTo(sesionNueva.getFechaHasta()) > 0 ){
			//Deberia de ser menor
			bindingResult.resolveMessageCodes("La fecha de inicio del curso debe de ser menor a la de finalizacion.", "nuevaSesion.fechaDesde");
			valida = false;
		}
		
		//TODO FALTA ESTA
//		Calendar fechaActual = Calendar.getInstance();
//		if(sesionNueva.getFechaDesde().compareTo(sesionNueva.getFechaHasta()) > 0 ){
//			//Deberia de ser menor
//			bindingResult.resolveMessageCodes("La fecha de inicio del curso debe de ser menor a la de finalizacion.", "nuevaSesion.fechaDesde");
//			valida = false;
//		}
		
		return valida;
	}
}
