package org.educa.core.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.educa.core.controller.forms.UnidadForm;
import org.educa.core.dao.ExamenUnidadRepository;
import org.educa.core.dao.MaterialUnidadRepository;
import org.educa.core.dao.UnidadRepository;
import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.EstadoCurso;
import org.educa.core.entities.model.ExamenUnidad;
import org.educa.core.entities.model.ExamenUnidadId;
import org.educa.core.entities.model.MaterialUnidad;
import org.educa.core.entities.model.MaterialUnidadId;
import org.educa.core.entities.model.OpcionExamenUnidad;
import org.educa.core.entities.model.OpcionExamenUnidadId;
import org.educa.core.entities.model.PreguntaExamenUnidad;
import org.educa.core.entities.model.PreguntaExamenUnidadId;
import org.educa.core.entities.model.Unidad;
import org.educa.core.services.CursoService;
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
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@Controller
@RequestMapping("/detalleUnidad")
@SessionAttributes({ "unidadForm" })
public class DetalleUnidadController {

	private static final String DETALLE_CURSO = "views/curso/unidad/detalle";
	
	@Autowired
	private CursoService cursoService;
	
	@Autowired
	@Qualifier("materialUnidadRepository")
	private MaterialUnidadRepository materialUnidadRepository;
	
	@Autowired
	@Qualifier("unidadRepository")
	private UnidadRepository unidadRepository;
	
	@Autowired
	@Qualifier("examenUnidadRepository")
	private ExamenUnidadRepository examenUnidadRepository;
	
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
		
		String contenidoMaterialGuardado = null;
		if(unidad.getMaterial() != null && !unidad.getMaterial().isEmpty()){
			contenidoMaterialGuardado = new String(unidad.getMaterial().get(0).getMaterial());
		}		
		unidadForm.setMaterialTeorico(contenidoMaterialGuardado);
		
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
		
	@RequestMapping(value = "/guardarMaterialTeorico", method = RequestMethod.POST)
	public String guardarMaterialTeorico(@ModelAttribute @Valid UnidadForm unidadForm, BindingResult bindingResult, Model model) {
		System.out.println("Texto de material guardado: " + unidadForm.getMaterialTeorico());
		
		if(!validaContenidoMaterialUnidad(unidadForm)){
			model.addAttribute("mostrarErrorContenidoMaterialTeoricoVacio", true);
			model.addAttribute("mensajeErrorContenidoMaterialTeoricoVacio", "Debe de cargar material teórico para poder guardarlo.");
			
			model.addAttribute("unidadForm", unidadForm);
			model.addAttribute("mostrarTabMaterialTeorico", true);
			model.addAttribute("mostrarTabVideo", false);
			model.addAttribute("mostrarTabPracticas", false);
			model.addAttribute("mostrarTabExamen", false);
			model.addAttribute("mostrarMensajeCantidadPreguntas", true);
			
			return DETALLE_CURSO;
		}
		
		
		Unidad unidad = unidadRepository.findOne(unidadForm.getUnidad().getId());//Voy a buscarla porque las listas se cargaron en otra sesion de hibernate (no en la actual).
		MaterialUnidad material = null;
		if(unidad.getMaterial() == null || unidad.getMaterial().isEmpty()){
			material = new MaterialUnidad();
			
			MaterialUnidadId id = new MaterialUnidadId();
			id.setIdCurso(unidadForm.getCurso().getId());
			id.setIdMaterial(new Long(unidad.getId().getNumero()));
			id.setNumero(1);
			
			material.setId(id);
			unidad.addMaterial(material);
			
			material.setUnidad(unidad);
			material.setIdCurso(unidad.getCurso().getId());
		} else {
			material = unidad.getMaterial().get(0);
		}
		
		byte[] materialBytes = unidadForm.getMaterialTeorico().getBytes();		
		material.setMaterial(materialBytes);
		
		materialUnidadRepository.save(material);
		
		Unidad unidadCompleta = unidadRepository.findOne(unidad.getId());		
		unidadForm.setUnidad(unidadCompleta);
		
		String contenidoMaterialGuardado = null;
		if(unidadCompleta.getMaterial() != null && !unidadCompleta.getMaterial().isEmpty()){
			contenidoMaterialGuardado = new String(unidadCompleta.getMaterial().get(0).getMaterial());
		}		
		unidadForm.setMaterialTeorico(contenidoMaterialGuardado);
		
		model.addAttribute("unidadForm", unidadForm);
		model.addAttribute("mostrarTabMaterialTeorico", true);
		model.addAttribute("mostrarTabVideo", false);
		model.addAttribute("mostrarTabPracticas", false);
		model.addAttribute("mostrarTabExamen", false);
		model.addAttribute("mostrarMensajeCantidadPreguntas", true);
		model.addAttribute("mostrarMensajeMaterialGuardado", true);
		
		return DETALLE_CURSO;
	}

	private boolean validaContenidoMaterialUnidad(UnidadForm unidadForm) {
		boolean valido = true;
		
		//TODO SACAR ESTO DESDE ACA
		if(valido){
			return valido;
		}
		//TODO SACAR ESTO HASTA ACA
		
		//TODO REVISAR ESTO XQ NO ANDA BIEN!
		if(unidadForm.getMaterialTeorico() == null || unidadForm.getMaterialTeorico().isEmpty()){
			valido = false;
		} else {
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				String xmlConCabecera = "<?xml version=\"1.0\"?>" + unidadForm.getMaterialTeorico();
				ByteArrayInputStream input =  new ByteArrayInputStream(xmlConCabecera.getBytes("utf-8"));
				Document doc = builder.parse(input);
				String contenido = doc.getTextContent();
				
				// builder.parse(new InputSource("<?xml version=\"1.0\"?><body>" + unidadForm.getMaterialTeorico() + "</body>"  ))

				
				if(contenido == null || contenido.isEmpty()){
					valido = false;
				}				
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
		}
		
		return valido;
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
			id.setNumero(unidadForm.getUnidad().getId().getNumero());
			id.setIdExamen(1);
			
			examenUnidad.setId(id);
		}
		
		examenUnidad.setCantPreguntasUsuario(unidadForm.getCantidadPreguntasAlumno());
		
		List<PreguntaExamenUnidad> preguntas = examenUnidad.getPreguntas();
		if(preguntas == null){
			preguntas = new ArrayList<PreguntaExamenUnidad>();
		}
		
		PreguntaExamenUnidadId preguntaId = new PreguntaExamenUnidadId();
		preguntaId.setIdCurso(unidadForm.getCurso().getId());
		preguntaId.setNumero(unidadForm.getUnidad().getId().getNumero());
		preguntaId.setIdExamen(1);
		preguntaId.setIdPregunta(preguntas.size()+1);
		
		List<OpcionExamenUnidad> opciones = new ArrayList<OpcionExamenUnidad>();
		
		OpcionExamenUnidadId opcionId1 = new OpcionExamenUnidadId();
		opcionId1.setIdCurso(unidadForm.getCurso().getId());
		opcionId1.setNumero(unidadForm.getUnidad().getId().getNumero());
		opcionId1.setIdExamen(1);
		opcionId1.setIdPregunta(preguntaId.getIdPregunta());
		opcionId1.setIdOpcion(1);
		
		OpcionExamenUnidad opcion1 = new OpcionExamenUnidad();
		opcion1.setId(opcionId1);
		opcion1.setEsCorrecta(unidadForm.isOpcionUnoSeleccionada());
		opcion1.setTexto(unidadForm.getRespuestaOpcionUno());
		
		OpcionExamenUnidadId opcionId2 = new OpcionExamenUnidadId();
		opcionId2.setIdCurso(unidadForm.getCurso().getId());
		opcionId2.setNumero(unidadForm.getUnidad().getId().getNumero());
		opcionId2.setIdExamen(1);
		opcionId2.setIdPregunta(preguntaId.getIdPregunta());
		opcionId2.setIdOpcion(2);
		
		OpcionExamenUnidad opcion2 = new OpcionExamenUnidad();
		opcion2.setId(opcionId2);
		opcion2.setEsCorrecta(unidadForm.isOpcionDosSeleccionada());
		opcion2.setTexto(unidadForm.getRespuestaOpcionDos());
		
		OpcionExamenUnidadId opcionId3 = new OpcionExamenUnidadId();
		opcionId3.setIdCurso(unidadForm.getCurso().getId());
		opcionId3.setNumero(unidadForm.getUnidad().getId().getNumero());
		opcionId3.setIdExamen(1);
		opcionId3.setIdPregunta(preguntaId.getIdPregunta());
		opcionId3.setIdOpcion(3);
		
		OpcionExamenUnidad opcion3 = new OpcionExamenUnidad();
		opcion3.setId(opcionId3);
		opcion3.setEsCorrecta(unidadForm.isOpcionTresSeleccionada());
		opcion3.setTexto(unidadForm.getRespuestaOpcionTres());
		
		OpcionExamenUnidadId opcionId4 = new OpcionExamenUnidadId();
		opcionId4.setIdCurso(unidadForm.getCurso().getId());
		opcionId4.setNumero(unidadForm.getUnidad().getId().getNumero());
		opcionId4.setIdExamen(1);
		opcionId4.setIdPregunta(preguntaId.getIdPregunta());
		opcionId4.setIdOpcion(4);
		
		OpcionExamenUnidad opcion4 = new OpcionExamenUnidad();
		opcion4.setId(opcionId4);
		opcion4.setEsCorrecta(unidadForm.isOpcionCuatroSeleccionada());
		opcion4.setTexto(unidadForm.getRespuestaOpcionCuatro());
		
		opciones.add(opcion1);
		opciones.add(opcion2);
		opciones.add(opcion3);
		opciones.add(opcion4);
		
		
		PreguntaExamenUnidad pregunta = new PreguntaExamenUnidad();
		pregunta.setId(preguntaId);
		pregunta.setEnunciado(unidadForm.getPregunta());
		pregunta.setMultipleChoica(true);
		pregunta.setOpciones(opciones);
		
		preguntas.add(pregunta);
		
		examenUnidad.setPreguntas(preguntas);
		
		examenUnidadRepository.save(examenUnidad);
		
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
		if(examenUnidad == null){
			examenUnidad = new ExamenUnidad();
			
			ExamenUnidadId id = new ExamenUnidadId();
			id.setIdCurso(unidadForm.getCurso().getId());
			id.setNumero(unidadForm.getUnidad().getId().getNumero());
			id.setIdExamen(1);
			
			examenUnidad.setId(id);
		}
		
		examenUnidad.setCantPreguntasUsuario(unidadForm.getCantidadPreguntasAlumno());
		
		List<PreguntaExamenUnidad> preguntas = examenUnidad.getPreguntas();
		if(preguntas == null){
			preguntas = new ArrayList<PreguntaExamenUnidad>();
		}
		
		PreguntaExamenUnidadId preguntaId = new PreguntaExamenUnidadId();
		preguntaId.setIdCurso(unidadForm.getCurso().getId());
		preguntaId.setNumero(unidadForm.getUnidad().getId().getNumero());
		preguntaId.setIdExamen(1);
		preguntaId.setIdPregunta(preguntas.size()+1);
		
		PreguntaExamenUnidad pregunta = new PreguntaExamenUnidad();
		pregunta.setId(preguntaId);
		pregunta.setEnunciado(unidadForm.getPregunta());
		pregunta.setMultipleChoica(false);
		pregunta.setRespuesta(unidadForm.getRespuestaUnica());
		
		preguntas.add(pregunta);
		
		examenUnidad.setPreguntas(preguntas);
		
		examenUnidadRepository.save(examenUnidad);
		
		return examenUnidad;
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
