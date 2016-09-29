package org.educa.core.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.educa.core.controller.forms.CursoForm;
import org.educa.core.dao.SesionRepository;
import org.educa.core.dao.UnidadRepository;
import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.Sesion;
import org.educa.core.entities.model.Unidad;
import org.educa.core.services.CursoService;
import org.educa.core.util.FechaUtil;
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
		
		cargarValoresBasicosParaUnidad(model, false, false, false, false, false);
		cargarValoresBasicosParaSesion(curso, model, false, false, false, false, false);
		
		CursoForm cursoForm = new CursoForm();
		cursoForm.setCurso(curso);
		
		if(curso == null || curso.getUnidades() == null || curso.getUnidades().isEmpty()){
			model.addAttribute("mostrarMensajeErrorCrearSesionSinUnidad", true);			
		}
		
		model.addAttribute("cursoForm", cursoForm);
		model.addAttribute("mostrarTabUnidad", true);		
		
		return CONFIGURACION_CURSO;
	}
	
	@RequestMapping(value = "/configuracionUnidadCurso", method = RequestMethod.POST)
	public String guardarUnidadCurso(@ModelAttribute @Valid CursoForm cursoForm, BindingResult bindingResult, Model model) {
		Curso curso = cursoForm.getCurso();
		model.addAttribute("mostrarTabUnidad", true);
		if (bindingResult.hasFieldErrors("nuevaUnidad.*")) {
			model.addAttribute("cursoForm", cursoForm);			
			cargarValoresBasicosParaUnidad(model, false, false, false, false, true);
			cargarValoresBasicosParaSesion(curso, model, false, false, false, false, false);			
			
			return CONFIGURACION_CURSO;
		}
		
		//Determino si se creo una nueva unidad
		Unidad unidadNueva = cursoForm.getNuevaUnidad();		
		if(unidadNueva != null){			
			this.cursoService.crearUnidad(curso, unidadNueva);			
			cargarValoresBasicosParaUnidad(model, true, false, false, false, true);			
		}
		
		Curso cursoHidratado = this.cursoService.encontrarCursoPorIdHidratado(curso.getId());
		//Seteo los nuevos valores
		cursoForm = new CursoForm();
		cursoForm.setCurso(cursoHidratado);
		
		model.addAttribute("cursoForm", cursoForm);
		cargarValoresBasicosParaSesion(cursoHidratado, model, false, false, false, false, false);
		
		return CONFIGURACION_CURSO;
	}
	
	@RequestMapping(value = "/actualizarUnidad/{idCurso}", method = RequestMethod.POST)
	public String actualizarUnidad(@PathVariable("idCurso") long idCurso, @ModelAttribute("unidad") @Valid Unidad unidad, BindingResult bindingResult, Model model) {		
		Curso curso = this.cursoService.encontrarCursoPorId(idCurso);
		CursoForm cursoForm = new CursoForm();
		cursoForm.setCurso(curso);
		model.addAttribute("mostrarTabUnidad", true);
		
		if (bindingResult.hasErrors()) {			
			cargarValoresBasicosParaUnidad(model, false, false, true, false, false);
			cargarValoresBasicosParaSesion(curso, model, false, false, false, false, false);			
			model.addAttribute("cursoForm", cursoForm);
			
			List<String> errores = new ArrayList<String>();
			for(ObjectError error : bindingResult.getAllErrors()){
				errores.add(error.getDefaultMessage());				
			}
			
			model.addAttribute("descripcionUnidadModificada", unidad.getDescripcionLargaError());
			model.addAttribute("erroresActualizacionUnidad", errores);
			
			return CONFIGURACION_CURSO;
		}
		
		unidad.setCurso(curso);
		this.unidadRepository.save(unidad);
		Curso cursoHidratado = this.cursoService.encontrarCursoPorIdHidratado(curso.getId());		
		cursoForm.setCurso(cursoHidratado);
		
		model.addAttribute("cursoForm", cursoForm);
		
		cargarValoresBasicosParaUnidad(model, false, true, false, false, false);
		cargarValoresBasicosParaSesion(cursoHidratado, model, false, false, false, false, false);
		
		return CONFIGURACION_CURSO;		
	}
	
	@RequestMapping(value = "/eliminarUnidad/{idCurso}/{idUnidad}/{numeroUnidad}", method = RequestMethod.GET)
	public String eliminarUnidad(@PathVariable("idCurso") long idCurso, @PathVariable("idUnidad") long idUnidad, @PathVariable("numeroUnidad") int numeroUnidad, Model model) {
		Curso curso = this.cursoService.encontrarCursoPorId(idCurso);
		model.addAttribute("mostrarTabUnidad", true);
		boolean eliminada = false;
		if(curso != null && curso.getUnidades() != null){
			eliminada = this.cursoService.eliminarUnidadCurso(curso, idUnidad, numeroUnidad);			
		}
		
		Curso cursoHidratado = this.cursoService.encontrarCursoPorIdHidratado(idCurso);		
		//Seteo los nuevos valores		
		CursoForm cursoForm = new CursoForm();
		cursoForm.setCurso(cursoHidratado);
		
		model.addAttribute("cursoForm", cursoForm);
		
		cargarValoresBasicosParaUnidad(model, false, false, false, eliminada, false);
		cargarValoresBasicosParaSesion(cursoHidratado, model, false, false, false, false, false);
				
		return CONFIGURACION_CURSO;
	}
	
	@RequestMapping(value = "/configuracionSesionCurso", method = RequestMethod.POST)
	public String guardarSesionCurso(@ModelAttribute @Valid CursoForm cursoForm, BindingResult bindingResult, Model model) {
		Curso curso = cursoForm.getCurso();
		model.addAttribute("mostrarTabUnidad", false);
		
		if (bindingResult.hasFieldErrors("nuevaSesion.*")) {
			validarFechasSesion(cursoForm.getNuevaSesion(), bindingResult);
			model.addAttribute("cursoForm", cursoForm);
			cargarValoresBasicosParaUnidad(model, false, false, false, false, false);
			cargarValoresBasicosParaSesion(curso, model, false, false, false, false, true);
			
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
				cargarValoresBasicosParaUnidad(model, false, false, false, false, false);
				cargarValoresBasicosParaSesion(curso, model, false, false, false, false, true);
				
				return CONFIGURACION_CURSO;
			}
			 
			sesionNueva = cargarFechasFinSesion(sesionNueva);
			this.cursoService.crearSesion(curso, sesionNueva);
			
			cursoHidratado = this.cursoService.encontrarCursoPorIdHidratado(curso.getId());
			cargarValoresBasicosParaSesion(cursoHidratado, model, true, false, false, false, true);
		}
				
		//Seteo los nuevos valores
		cursoForm = new CursoForm();
		cursoForm.setCurso(cursoHidratado);
		
		model.addAttribute("cursoForm", cursoForm);		
		cargarValoresBasicosParaUnidad(model, false, false, false, false, false);		
		
		return CONFIGURACION_CURSO;
	}
	
	@RequestMapping(value = "/actualizarSesion/{idCurso}", method = RequestMethod.POST)
	public String actualizarSesion(@PathVariable("idCurso") long idCurso, @ModelAttribute("sesion") @Valid Sesion sesion, BindingResult bindingResult, Model model) {		
		Curso curso = this.cursoService.encontrarCursoPorId(idCurso);
		CursoForm cursoForm = new CursoForm();
		cursoForm.setCurso(curso);
		model.addAttribute("mostrarTabUnidad", false);
		
		if (bindingResult.hasErrors()) {
			validarFechasSesion(sesion, bindingResult);
			cargarValoresBasicosParaUnidad(model, false, false, false, false, false);
			cargarValoresBasicosParaSesion(curso, model, false, false, true, false, false);			
			model.addAttribute("cursoForm", cursoForm);
			
			List<String> errores = new ArrayList<String>();
			for(ObjectError error : bindingResult.getAllErrors()){
				errores.add(error.getDefaultMessage());				
			}
			
			model.addAttribute("descripcionSesionModificada", sesion.getDescripcionLargaError());
			model.addAttribute("erroresActualizacionSesion", errores);
			
			return CONFIGURACION_CURSO;
		}
		
		sesion = cargarFechasFinSesion(sesion);
		sesion.setCurso(curso);
		this.sesionRepository.save(sesion);
		Curso cursoHidratado = this.cursoService.encontrarCursoPorIdHidratado(curso.getId());		
		cursoForm.setCurso(cursoHidratado);
		
		model.addAttribute("cursoForm", cursoForm);
		
		cargarValoresBasicosParaUnidad(model, false, false, false, false, false);
		cargarValoresBasicosParaSesion(cursoHidratado, model, false, true, false, false, false);
		
		return CONFIGURACION_CURSO;		
	}
	
	@RequestMapping(value = "/eliminarSesion/{idCurso}/{idSesion}/{numeroSesion}", method = RequestMethod.GET)
	public String eliminarSesion(@PathVariable("idCurso") long idCurso, @PathVariable("idSesion") long idSesion, @PathVariable("numeroSesion") int numeroSesion, Model model) {
		model.addAttribute("mostrarTabUnidad", false);
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
		
		cargarValoresBasicosParaUnidad(model, false, false, false, false, false);
		cargarValoresBasicosParaSesion(cursoHidratado, model, false, false, false, eliminada, false);
				
		return CONFIGURACION_CURSO;
	}
	
	private void cargarValoresBasicosParaUnidad(Model model, boolean alta, boolean actualizacion, boolean actualizacionError, boolean elimacion, boolean mostrarErroresUnidad){
		model.addAttribute("unidad", new Unidad());
		model.addAttribute("mostrarMensajeAltaUnidad", alta);
		model.addAttribute("mostrarMensajeErrorActualizacionUnidad", actualizacionError);
		model.addAttribute("mostrarMensajeActualizacionUnidad", actualizacion);
		model.addAttribute("mostrarMensajeEliminarUnidad", elimacion);
		model.addAttribute("mostrarErroresUnidad", mostrarErroresUnidad);		
	}
	
	private void cargarValoresBasicosParaSesion(Curso curso, Model model, boolean alta, boolean actualizacion, boolean actualizacionError, boolean elimacion, boolean mostrarErroresSesion){
		if(curso == null || curso.getUnidades() == null || curso.getUnidades().isEmpty()){
			model.addAttribute("mostrarMensajeErrorCrearSesionSinUnidad", true);			
		}
		
		model.addAttribute("sesion", new Sesion());
		model.addAttribute("mostrarMensajeAltaSesion", alta);
		model.addAttribute("mostrarMensajeActualizacionSesion", actualizacion);
		model.addAttribute("mostrarMensajeErrorActualizacionSesion", actualizacionError);
		model.addAttribute("mostrarMensajeEliminarSesion", elimacion);
		model.addAttribute("mostrarErroresSesion", mostrarErroresSesion);
	}
	
	private boolean validarFechasSesion(Sesion sesionNueva, BindingResult bindingResult) {
		if(sesionNueva == null || bindingResult == null){
			return false;
		}
		boolean valida = true;
		if(sesionNueva.getFechaDesdeInscripcion() != null && sesionNueva.getFechaDesde() != null && sesionNueva.getFechaHasta() != null && ( sesionNueva.getFechaDesdeInscripcion().compareTo(sesionNueva.getFechaDesde()) > 0 
				|| sesionNueva.getFechaDesdeInscripcion().compareTo(sesionNueva.getFechaHasta()) > 0)){
			//Deberia de ser menor
			bindingResult.rejectValue("nuevaSesion.fechaDesdeInscripcion", "ERROR-FECHA INSCRIPCION", "La fecha de inscripcion debe de ser menor al resto de las fechas.");
			valida = false;
		}
		
		if(sesionNueva.getFechaDesde() != null && sesionNueva.getFechaHasta() != null && sesionNueva.getFechaDesde().compareTo(sesionNueva.getFechaHasta()) > 0 ){
			//Deberia de ser menor
			bindingResult.rejectValue("nuevaSesion.fechaDesde", "ERROR-FECHA INICIO" ,"La fecha de inicio del curso debe de ser menor a la de finalizacion.");
			valida = false;
		}
		
		Calendar fechaActual = Calendar.getInstance();
		Date fechaActualFormateada = FechaUtil.formateFechaDDMMYYYYEs(fechaActual.getTime());
		if(sesionNueva.getFechaDesdeInscripcion() != null && sesionNueva.getFechaDesdeInscripcion().compareTo(fechaActualFormateada) < 0 ){
			//Deberia de ser menor
			bindingResult.rejectValue("nuevaSesion.fechaDesdeInscripcion", "ERROR-FECHA INSCRIPCION-ACTUAL", "La fecha de inscripcion no puede ser anterior a la fecha actual.");
			valida = false;
		}
		
		if(sesionNueva.getFechaDesde() != null && sesionNueva.getFechaDesde().compareTo(fechaActualFormateada) < 0 ){
			//Deberia de ser menor
			bindingResult.rejectValue("nuevaSesion.fechaDesde", "ERROR-FECHA INICIO-ACTUAL", "La fecha de inicio del curso no puede ser anterior a la fecha actual.");
			valida = false;
		}
		
		if(sesionNueva.getFechaHasta() != null && sesionNueva.getFechaHasta().compareTo(fechaActualFormateada) < 0 ){
			//Deberia de ser menor
			bindingResult.rejectValue("nuevaSesion.fechaHasta", "ERROR-FECHA HASTA-ACTUAL", "La fecha de fin del curso no puede ser anterior a la fecha actual.");
			valida = false;
		}
		
		return valida;
	}
	
	private Sesion cargarFechasFinSesion(Sesion sesionNueva){		
		//Fecha de fin de inscripcion
		Calendar fecha = Calendar.getInstance();
		fecha.setTime(sesionNueva.getFechaDesde());
		fecha.add(Calendar.DATE, -1); //el -1 indica que se le restara 1 dias 
		Date fechaHastaInscripcion = FechaUtil.formateFechaDDMMYYYYEs(fecha.getTime());
		sesionNueva.setFechaHastaInscripcion(fechaHastaInscripcion);		
		
		return sesionNueva;
	}
}
