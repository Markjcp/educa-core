package org.educa.core.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.validation.Valid;

import org.educa.core.controller.forms.CursoForm;
import org.educa.core.dao.UnidadRepository;
import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.Sesion;
import org.educa.core.entities.model.Unidad;
import org.educa.core.entities.model.UnidadId;
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

	private static final String LISTADO_CURSO = "views/curso/listado-no-admin";
	private static final String CONFIGURACION_CURSO = "views/curso/configuracion-curso-no-admin";
	
	@Autowired
	private CursoService cursoService;
	
	@Autowired
	@Qualifier("unidadRepository")
	private UnidadRepository unidadRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		
		return LISTADO_CURSO;
	}
	
	@RequestMapping(value = "/configuracionCurso/{id}", method = RequestMethod.GET)
	public String configuracionCurso(@PathVariable("id") long id, Model model) {
		Curso curso = this.cursoService.encontrarCursoPorId(id);
		if(curso != null && curso.getUnidades() == null){
			curso.setUnidades(new TreeSet<Unidad>());
		}
		
		if(curso != null && curso.getSesiones() == null){
			curso.setSesiones(new ArrayList<Sesion>());
		}
		
		CursoForm cursoForm = new CursoForm();
		cursoForm.setCurso(curso);
		
		model.addAttribute("cursoForm", cursoForm);
		model.addAttribute("mostrarMensajeAltaUnidad", false);		
		model.addAttribute("unidad", new Unidad());
		
		return CONFIGURACION_CURSO;
	}
	
	@RequestMapping(value = "/configuracionCurso", method = RequestMethod.POST)
	public String guardarCurso(@ModelAttribute @Valid CursoForm cursoForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasFieldErrors("curso.*") || bindingResult.hasFieldErrors("nuevaUnidad.*")) {
			model.addAttribute("cursoForm", cursoForm);
			model.addAttribute("mostrarMensajeAltaUnidad", false);
			model.addAttribute("unidad", new Unidad());
			
			return CONFIGURACION_CURSO;
		}
		
		Curso curso = cursoForm.getCurso();
		Unidad unidadNueva = cursoForm.getNuevaUnidad();
		UnidadId unidadId = new UnidadId();
		unidadId.setIdCurso(curso.getId());
		
		//Seria la primer unidad dada de alta para el curso si unidades esta en null o vacio
		int nroNuevaUnidad = 1;
		
		//Para guardar una unidad le asigno un numero, el cual no puede estar repetido y debe de seguir en orden.
		if(curso.getUnidades() != null && !curso.getUnidades().isEmpty()){
			for(Unidad unidad : curso.getUnidades()){
				if(unidad.getId().getNumero() > nroNuevaUnidad){
					nroNuevaUnidad = unidad.getId().getNumero();
				}
			}
			
			//Incremento porque guarda el nro de unidad mas grande dentro del curso
			nroNuevaUnidad++;
		}
		
		curso.addUnidad(unidadNueva);
		unidadId.setNumero(nroNuevaUnidad);
		unidadNueva.setId(unidadId);		
		unidadNueva.setCurso(curso);
		
		this.unidadRepository.save(unidadNueva);
				
		Curso cursoHidratado = this.cursoService.encontrarCursoPorId(curso.getId());
		//Seteo los nuevos valores
		cursoForm = new CursoForm();
		cursoForm.setCurso(cursoHidratado);
		
		model.addAttribute("cursoForm", cursoForm);
		model.addAttribute("mostrarMensajeAltaUnidad", true);
		model.addAttribute("unidad", new Unidad());
		
		return CONFIGURACION_CURSO;
	}
	
	@RequestMapping(value = "/actualizarUnidad/{idCurso}", method = RequestMethod.POST)
	public String actualizarUnidad(@PathVariable("idCurso") long idCurso, @ModelAttribute("unidad") @Valid Unidad unidad, BindingResult bindingResult, Model model) {
		System.out.println("ME TENES CANSADA!");
		
		Curso curso = this.cursoService.encontrarCursoPorId(idCurso);
		CursoForm cursoForm = new CursoForm();
		cursoForm.setCurso(curso);
		
		if (bindingResult.hasErrors()) {			
			model.addAttribute("mostrarMensajeErrorActualizacion", true);
			model.addAttribute("cursoForm", cursoForm);
			model.addAttribute("mostrarMensajeAltaUnidad", false);
			model.addAttribute("unidad", unidad);
			
			List<String> errores = new ArrayList<String>();
			for(ObjectError error : bindingResult.getAllErrors()){
				errores.add(error.getDefaultMessage());				
			}
			
			model.addAttribute("descripcionUnidadModificada", unidad.getDescripcionLarga());
			model.addAttribute("erroresActualizacion", errores);
			
			return CONFIGURACION_CURSO;
		}
		
		unidad.setCurso(curso);
		this.unidadRepository.save(unidad);
		Curso cursoHidratado = this.cursoService.encontrarCursoPorId(curso.getId());		
		cursoForm.setCurso(cursoHidratado);
		
		model.addAttribute("cursoForm", cursoForm);
		model.addAttribute("mostrarMensajeAltaUnidad", false);
		model.addAttribute("mostrarMensajeActualizacionUnidad", true);
		model.addAttribute("unidad", new Unidad());
		
		return CONFIGURACION_CURSO;		
	}
	
	@RequestMapping(value = "/eliminarUnidad/{idCurso}/{idUnidad}/{numeroUnidad}", method = RequestMethod.GET)
	public String eliminarUnidad(@PathVariable("idCurso") long idCurso, @PathVariable("idUnidad") long idUnidad, @PathVariable("numeroUnidad") int numeroUnidad, Model model) {
		Curso curso = this.cursoService.encontrarCursoPorId(idCurso);
		
		//TODO ESTO DEBERIA DE IR AL SERVICE DE CURSO
		if(curso != null && curso.getUnidades() != null){
			SortedSet<Unidad> unidades = new TreeSet<Unidad>();
			boolean encontrado = false;
			Unidad unidadEliminar = null;
			for(Unidad unidad : curso.getUnidades()){
				if(unidad.getId().getIdCurso() == idUnidad && unidad.getId().getNumero() == numeroUnidad){
					encontrado = true;
					unidadEliminar = unidad;
				} else {
					unidades.add(unidad);
				}
			}
			
			if(encontrado){
				int nroUnidad = unidadEliminar.getId().getNumero();		
				this.unidadRepository.delete(unidadEliminar);
				
				curso = this.cursoService.encontrarCursoPorId(idCurso);
				
				//TODO Elimino las unidades porque sino no me deja actualizarle el nro de unidad (OjO con esto, no esta bien; hay que arreglarlo para los proximos sprint [ediaz])
				/*for(Unidad unidad : unidades){
					this.unidadRepository.delete(unidad);
				}
				si tengo q dejar esto => debo de crear primero las unidades para asignarselas al curso!
				*/
				
				//Actualizo el numero de unidad
				SortedSet<Unidad> unidadesNroOk = new TreeSet<Unidad>();
				for(Unidad unidad : unidades){
					if(unidad.getId().getNumero() > nroUnidad){
						unidad.getId().setNumero(nroUnidad);
						nroUnidad++;
					}
					
					Unidad unidadPersistida = this.unidadRepository.save(unidad);
					unidadesNroOk.add(unidadPersistida);
				}
				
				curso.setUnidades(unidadesNroOk);
				this.cursoService.guardarCurso(curso);
				model.addAttribute("mostrarMensajeEliminarUnidad", true);							
			}
		}
		
		Curso cursoHidratado = this.cursoService.encontrarCursoPorId(idCurso);		
		//Seteo los nuevos valores		
		CursoForm cursoForm = new CursoForm();
		cursoForm.setCurso(cursoHidratado);
		
		model.addAttribute("cursoForm", cursoForm);
		model.addAttribute("mostrarMensajeAltaUnidad", false);
		model.addAttribute("mostrarMensajeActualizacionUnidad", false);		
		model.addAttribute("unidad", new Unidad());
				
		return CONFIGURACION_CURSO;
	}	
}
