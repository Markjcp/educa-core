package org.educa.core.controller;

import javax.validation.Valid;

import org.educa.core.controller.forms.UnidadForm;
import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.EstadoCurso;
import org.educa.core.entities.model.ExamenUnidad;
import org.educa.core.entities.model.ExamenUnidadId;
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
		
		model.addAttribute("unidadForm", unidadForm);
		model.addAttribute("mostrarTabMaterialTeorico", true);
		model.addAttribute("mostrarTabVideo", false);
		model.addAttribute("mostrarTabPracticas", false);
		model.addAttribute("mostrarTabExamen", false);
		model.addAttribute("mostrarMensajeCantidadPreguntas", true);
		
		return DETALLE_CURSO;
	}
	
	@RequestMapping(value = "/cambiarEstadoPublicacion", method = RequestMethod.POST)
	public String cambiarEstadoPublicacion(@ModelAttribute @Valid UnidadForm unidadForm, BindingResult bindingResult, Model model) {
		/*
		 * OjO: Viene con el valor con el que fue en el formulario en la accion anterior, 
		 * asi que hay que cambiarle el valor.
		 */
		
		EstadoCurso nuevoEstadoCurso = EstadoCurso.NO_PUBLICADO;
		if(!unidadForm.isPublicado()){
			nuevoEstadoCurso = EstadoCurso.PUBLICADO;
		}
		Curso curso = unidadForm.getCurso();
		if(EstadoCurso.PUBLICADO.equals(nuevoEstadoCurso) && (curso.getUnidades() == null || curso.getUnidades().isEmpty())){
			model.addAttribute("mostrarMensajeErrorPublicacion", true);
			StringBuilder mensaje = new StringBuilder();
			mensaje.append("El curso '" + curso.getNombre() + "' ");
			mensaje.append("no se puede publicar porque no tiene unidades cargadas.");					
			model.addAttribute("mensajeErrorPublicacion", mensaje.toString());
			
			model.addAttribute("unidadForm", unidadForm);
			model.addAttribute("mostrarTabMaterialTeorico", true);
			model.addAttribute("mostrarTabVideo", false);
			model.addAttribute("mostrarTabPracticas", false);
			model.addAttribute("mostrarTabExamen", false);
			model.addAttribute("mostrarMensajeCantidadPreguntas", true);
			
			return DETALLE_CURSO;
		}
				
		curso.setEstadoCurso(nuevoEstadoCurso);
		
		cursoService.guardarCurso(curso);
		
		Curso cursoPersistido = cursoService.encontrarCursoPorId(curso.getId());
		unidadForm.setCurso(cursoPersistido);
		
		unidadForm.setPublicado(EstadoCurso.PUBLICADO.equals(cursoPersistido.getEstadoCurso()) ? true : false);
		
		model.addAttribute("unidadForm", unidadForm);
		model.addAttribute("mostrarTabMaterialTeorico", true);
		model.addAttribute("mostrarTabVideo", false);
		model.addAttribute("mostrarTabPracticas", false);
		model.addAttribute("mostrarTabExamen", false);
		model.addAttribute("mostrarMensajeCantidadPreguntas", true);
		
		return DETALLE_CURSO;
	}	
	
	@RequestMapping(value = "/guardarPreguntaExamen", method = RequestMethod.POST)
	public String guardarPreguntaExamen(@ModelAttribute @Valid UnidadForm unidadForm, BindingResult bindingResult, Model model) {
		//TODO FALTA HACERLO!!!
		boolean valida = false;
		if(unidadForm.isMultipleChoice()){
			valida = validaMultipleChoice(unidadForm, bindingResult, model); 
			if(valida){
				cargarMultipleChoice(null, unidadForm);//TODO VER SI ES NULL
			} else {
				//ERROR DE VALIDACION - CARGA
			}			
		} else {
			valida = validaPreguntaSimple(unidadForm, bindingResult, model); 
			if(valida){
				cargarPreguntaSimple(null, unidadForm);//TODO VER SI ES NULL
			} else {
				//ERROR DE VALIDACION - CARGA
			}
		}
		
		if(!valida){
			
		}
		
		System.out.println("ENTRO PARA GUARDAR LA PREGUNTA DEL EXAMEN: " + unidadForm.isPublicado());
		if(unidadForm.isPublicado()){
			
		}
		
		model.addAttribute("unidadForm", unidadForm);
		model.addAttribute("mostrarTabMaterialTeorico", true);
		model.addAttribute("mostrarTabVideo", false);
		model.addAttribute("mostrarTabPracticas", false);
		model.addAttribute("mostrarTabExamen", false);
		model.addAttribute("mostrarMensajeCantidadPreguntas", true);
		
		return DETALLE_CURSO;
	}

	private boolean validaMultipleChoice(UnidadForm unidadForm, BindingResult bindingResult, Model model) {		
		boolean valida = validaCamposGeneral(unidadForm, bindingResult, model);
		
		//Valido cantidad de preguntas seleccionadas como correctas - debe de ser una.
		int cantidadPreguntasValidas = 0;
		if(unidadForm.isOpcionUnoSeleccionada()){
			cantidadPreguntasValidas++;
		}		
		if(unidadForm.isOpcionDosSeleccionada()){
			cantidadPreguntasValidas++;
		}
		if(unidadForm.isOpcionTresSeleccionada()){
			cantidadPreguntasValidas++;
		}
		if(unidadForm.isOpcionCuatroSeleccionada()){
			cantidadPreguntasValidas++;
		}
		
		if(cantidadPreguntasValidas == 0){
			model.addAttribute("ErrorCantidadRespuestasCorrectas", "Debe de seleccionar una respuesta como correcta.");
			valida = false;
		}
		if(cantidadPreguntasValidas > 1){
			model.addAttribute("ErrorCantidadRespuestasCorrectas", "Debe de seleccionar una única respuesta como correcta.");
			valida = false;
		}
		
		//Las opciones de respuesta no deben de ser vacias ni nulas
		if(unidadForm.getRespuestaOpcionUno() == null || unidadForm.getRespuestaOpcionUno().isEmpty()){
			bindingResult.rejectValue("respuestaOpcionUna", "ERROR-RTA-OPCION-UNO" ,"Debe de ingresar una opción de respuesta.");
			valida = false;
		}
		if(unidadForm.getRespuestaOpcionDos() == null || unidadForm.getRespuestaOpcionDos().isEmpty()){
			bindingResult.rejectValue("respuestaOpcionUna", "ERROR-RTA-OPCION-UNO" ,"Debe de ingresar una opción de respuesta.");
			valida = false;
		}
		if(unidadForm.getRespuestaOpcionTres() == null || unidadForm.getRespuestaOpcionTres().isEmpty()){
			bindingResult.rejectValue("respuestaOpcionTres", "ERROR-RTA-OPCION-TRES" ,"Debe de ingresar una opción de respuesta.");
			valida = false;
		}
		if(unidadForm.getRespuestaOpcionCuatro() == null || unidadForm.getRespuestaOpcionCuatro().isEmpty()){
			bindingResult.rejectValue("respuestaOpcionCuatro", "ERROR-RTA-OPCION-CUATRO" ,"Debe de ingresar una opción de respuesta.");
			valida = false;
		}
		
		return valida;
	}

	private ExamenUnidad cargarMultipleChoice(ExamenUnidad examenUnidad, UnidadForm unidadForm) {
		if(examenUnidad == null){
			examenUnidad = new ExamenUnidad();
			
			ExamenUnidadId id = new ExamenUnidadId();
			id.setIdCurso(unidadForm.getCurso().getId());
			id.setNumero(1);
			id.setIdExamen(new Long(unidadForm.getUnidad().getId().getNumero()));
			
			examenUnidad.setId(id);
		}
		
		//examenUnidad.setEnunciado(unidadForm.getPregunta());
		
		examenUnidad.setCantPreguntasUsuario(unidadForm.getCantidadPreguntasAlumno());
		examenUnidad.setMultipleChoica(true);
		
		//OpcionExamenUnidad
		
/*
		private ExamenUnidadId id;
		private String enunciado;
		private Integer cantPreguntasUsuario;
		private boolean multipleChoica;
		private String respuesta;	
		private Unidad unidad; //NO HACE FALTA	
		private List<OpcionExamenUnidad> opciones;

		----
		ID:
		
		private Integer numero;
		private Long idCurso;	
		private Long idExamen;
*/
		
		
		
		
		return examenUnidad;
	}
	
	private boolean validaPreguntaSimple(UnidadForm unidadForm, BindingResult bindingResult, Model model) {
		boolean valida = validaCamposGeneral(unidadForm, bindingResult, model);
		
		if(unidadForm.getRespuestaUnica() == null || unidadForm.getRespuestaUnica().isEmpty()){
			bindingResult.rejectValue("respuestaUnica", "ERROR-RTA-OPCION-CUATRO" ,"Debe de ingresar la respuesta correcta.");
			valida = false;
		}
		
		return valida;
	}

	private ExamenUnidad cargarPreguntaSimple(ExamenUnidad examenUnidad, UnidadForm unidadForm) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private boolean validaCamposGeneral(UnidadForm unidadForm, BindingResult bindingResult, Model model) {
		boolean valida = true;
		
		if(unidadForm.getCantidadPreguntasAlumno() != null && unidadForm.getCantidadPreguntasAlumno() < 1){
			//Deberia de ser mayor o igual a 1
			bindingResult.rejectValue("cantidadPreguntasAlumno", "ERROR-CANTIDAD-PREG-ALUMNO" ,"Debe de ingresar una cantidad mayor a 0 (número entero mayor a 0).");
			valida = false;
		}
		
		if(unidadForm.getPregunta() == null || unidadForm.getPregunta().isEmpty()){
			bindingResult.rejectValue("pregunta", "ERROR-PREGUNTA" ,"Debe de ingresar una pregunta.");
			valida = false;
		}
		
		return valida;
	}
}
