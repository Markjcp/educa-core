package org.educa.core.controller;

import javax.validation.Valid;

import org.educa.core.controller.forms.UnidadForm;
import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.EstadoCurso;
import org.educa.core.entities.model.Unidad;
import org.educa.core.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/detalleUnidad")
@SessionAttributes({ "unidadForm" })
public class DetalleUnidadController {

	private static final String DETALLE_CURSO = "views/curso/unidad/detalle";
	
	@Autowired
	private CursoService cursoService;
	
	@RequestMapping(value = "/{idCurso}/{idUnidad}/{numeroUnidad}", method = RequestMethod.GET)
	public String index(@PathVariable("idCurso") long idCurso, @PathVariable("idUnidad") long idUnidad, 
			@PathVariable("numeroUnidad") int numeroUnidad, Model model) {
		
		//Necesito: unidadForm , mostrarTabMaterialTeorico , mostrarTabVideo , mostrarTabPracticas , mostrarTabExamen
		//Metodo: cambiarEstado (form mas model)
		
		//No se validad que no sea null ya que no deberia de suceder, en caso de que pase, es porque hay un error entonces se espera verlo.
		Curso curso = this.cursoService.encontrarCursoPorId(idCurso);
		Unidad unidad = null;
		for(Unidad unaUnidad : curso.getUnidades()){
			if(unaUnidad.getId().getIdCurso() == idUnidad && unaUnidad.getId().getNumero() == numeroUnidad){
				unidad = unaUnidad;
				break;
			}
		}
		
		UnidadForm unidadForm = new UnidadForm();
		unidadForm.setCurso(curso);
		unidadForm.setUnidad(unidad);
		unidadForm.setPublicado(false);
		
		if(EstadoCurso.PUBLICADO.equals(curso.getEstadoCurso())){
			unidadForm.setPublicado(true);
		}
		
		//TODO BORRAR DESDE ACA
		unidadForm.setPublicado(true);
		//TODO BORRAR HASTA ACA
		
		model.addAttribute("unidadForm", unidadForm);
		model.addAttribute("mostrarTabMaterialTeorico", true);
		model.addAttribute("mostrarTabVideo", false);
		model.addAttribute("mostrarTabPracticas", false);
		model.addAttribute("mostrarTabExamen", false);
		
		return DETALLE_CURSO;
	}
	
	@RequestMapping(value = "/cambiarEstado", method = RequestMethod.POST)
	public String cambiarEstado(@ModelAttribute @Valid UnidadForm unidadForm, BindingResult bindingResult, Model model) {
		//TODO FALTA HACERLO!!!
		System.out.println("ENTRO PARA ACTUALIZAR EL ESTADO DE PUBLICADO: " + unidadForm.isPublicado());
		if(unidadForm.isPublicado()){
			
		}
		
		model.addAttribute("unidadForm", unidadForm);
		model.addAttribute("mostrarTabMaterialTeorico", true);
		model.addAttribute("mostrarTabVideo", false);
		model.addAttribute("mostrarTabPracticas", false);
		model.addAttribute("mostrarTabExamen", false);
		
		return DETALLE_CURSO;
	}
	
}
