package org.educa.core.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.educa.core.controller.forms.PreguntasForm;
import org.educa.core.controller.forms.UnidadForm;
import org.educa.core.dao.ExamenUnidadRepository;
import org.educa.core.dao.MaterialUnidadRepository;
import org.educa.core.dao.UnidadRepository;
import org.educa.core.dao.VideoUnidadRepository;
import org.educa.core.entities.constants.ConstantesDelModelo;
import org.educa.core.entities.model.ComponenteId;
import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.Estado;
import org.educa.core.entities.model.ExamenUnidad;
import org.educa.core.entities.model.ExamenUnidadId;
import org.educa.core.entities.model.MaterialUnidad;
import org.educa.core.entities.model.MaterialUnidadId;
import org.educa.core.entities.model.OpcionExamenUnidad;
import org.educa.core.entities.model.OpcionExamenUnidadId;
import org.educa.core.entities.model.PreguntaExamenUnidad;
import org.educa.core.entities.model.PreguntaExamenUnidadId;
import org.educa.core.entities.model.Unidad;
import org.educa.core.entities.model.VideoUnidad;
import org.educa.core.entities.model.VideoUnidadId;
import org.educa.core.services.CursoService;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
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
import org.springframework.web.multipart.MultipartFile;

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
	
	@Autowired
	@Qualifier("videoUnidadRepository")
	private VideoUnidadRepository videoUnidadRepository;	
	
	@RequestMapping(value = "/{idCurso}/{idUnidad}/{numeroUnidad}", method = RequestMethod.GET)
	public String index(@PathVariable("idCurso") long idCurso, @PathVariable("idUnidad") long idUnidad, 
			@PathVariable("numeroUnidad") int numeroUnidad, Model model) {		
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
		
		ExamenUnidadId examenId = new ExamenUnidadId();
		examenId.setIdCurso(curso.getId());
		examenId.setIdExamen(1);
		examenId.setNumero(unidad.getId().getNumero());
		
		ExamenUnidad examenUnidad = examenUnidadRepository.findOne(examenId);
		if(examenUnidad.getPreguntas() != null && !examenUnidad.getPreguntas().isEmpty()){
			List<PreguntasForm> preguntasForm = new ArrayList<PreguntasForm>();
			for(PreguntaExamenUnidad pregunta : examenUnidad.getPreguntas()){
				PreguntasForm preguntaForm = new PreguntasForm();
				preguntaForm.setPregunta(pregunta.getEnunciado());
				preguntaForm.setIdPregunta(pregunta.getId().getNumero());
				if(!pregunta.isMultipleChoice()){
					preguntaForm.setMultipleChoice(false);
					preguntaForm.setRespuestaUnica(pregunta.getRespuesta());					
				} else {
					preguntaForm.setMultipleChoice(true);
					//Opcion Uno
					OpcionExamenUnidad opcionUna = pregunta.getOpciones().get(0);
					preguntaForm.setOpcionUnoSeleccionada(opcionUna.isEsCorrecta());
					preguntaForm.setRespuestaOpcionUno(opcionUna.getTexto());
					//Opcion Dos
					OpcionExamenUnidad opcionDos = pregunta.getOpciones().get(1);
					preguntaForm.setOpcionDosSeleccionada(opcionDos.isEsCorrecta());
					preguntaForm.setRespuestaOpcionDos(opcionDos.getTexto());
					//Opcion Tres
					OpcionExamenUnidad opcionTres = pregunta.getOpciones().get(2);
					preguntaForm.setOpcionTresSeleccionada(opcionTres.isEsCorrecta());
					preguntaForm.setRespuestaOpcionTres(opcionTres.getTexto());
					//Opcion Cuatro
					OpcionExamenUnidad opcionCuatro = pregunta.getOpciones().get(3);
					preguntaForm.setOpcionCuatroSeleccionada(opcionCuatro.isEsCorrecta());
					preguntaForm.setRespuestaOpcionCuatro(opcionCuatro.getTexto());
					
				}
				preguntasForm.add(preguntaForm);
			}
			
			unidadForm.setPreguntas(preguntasForm);
		}
		
		unidadForm.setCurso(curso);
		unidadForm.setUnidad(unidad);
		unidadForm.setPublicado(false);
		unidadForm.setExamenUnidad(examenUnidad);
		
		String contenidoMaterialGuardado = null;
		if(unidad.getMaterial() != null && !unidad.getMaterial().isEmpty()){
			contenidoMaterialGuardado = new String(unidad.getMaterial().get(0).getMaterial());
		}		
		unidadForm.setMaterialTeorico(contenidoMaterialGuardado);
		
		unidadForm.setPublicado(unidad.isPublicado());		
		
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
		
		Estado nuevoEstadoCurso = Estado.NO_PUBLICADO;
		if(!unidadForm.isPublicado()){
			nuevoEstadoCurso = Estado.PUBLICADO;
		}
		
		//Voy a buscar la unidad nuevamente porque las colecciones estan cargadas en otra sesion de hibernate
		Unidad unidad = unidadRepository.findOne(unidadForm.getUnidad().getId());
		
		//Para poder publicar una unidad tiene que tener cargado un video y/o un material teorico.
		if(Estado.PUBLICADO.equals(nuevoEstadoCurso) && (unidad.getMaterial() == null || unidad.getMaterial().isEmpty()) 
				&& (unidad.getVideos() == null || unidad.getVideos().isEmpty())){
			String mensaje = "La unidad no se puede publicar porque no tiene ";
			boolean tieneMaterial = true;
			if(unidad.getMaterial() == null || unidad.getMaterial().isEmpty()){
				mensaje += "material teórico ";
				tieneMaterial = false;
			}
			
			if(unidad.getVideos() == null || unidad.getVideos().isEmpty()){
				if(!tieneMaterial){
					mensaje += "ni ";
				}
				
				mensaje += "video ";
			}
			
			mensaje += "cargado.";
			
			model.addAttribute("mostrarMensajeErrorPublicacion", true);				
			model.addAttribute("mensajeErrorPublicacion", mensaje);
			
			model.addAttribute("unidadForm", unidadForm);
			model.addAttribute("mostrarTabMaterialTeorico", true);
			model.addAttribute("mostrarTabVideo", false);
			model.addAttribute("mostrarTabPracticas", false);
			model.addAttribute("mostrarTabExamen", false);
			model.addAttribute("mostrarMensajeCantidadPreguntas", true);
			
			return DETALLE_CURSO;
		}
				
		unidad.setEstadoUnidad(nuevoEstadoCurso);
		
		unidadRepository.save(unidad);
		
		Unidad unidadPersistida = unidadRepository.findOne(unidad.getId());
		unidadForm.setUnidad(unidadPersistida);
		
		unidadForm.setPublicado(unidadPersistida.isPublicado());
		
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
		
		Unidad unidad = unidadRepository.findOne(unidadForm.getUnidad().getId());//Voy a buscarla porque las listas se cargaron en otra sesion de hibernate (no en la actual).
		
		boolean intentaLimpiar = intentaLimpiarContenido(unidadForm.getMaterialTeorico(), unidad);
		if(!intentaLimpiar){
			String mensajeError = validaContenidoMaterialUnidad(unidadForm);
			
			if(mensajeError != null && !mensajeError.isEmpty()){
				model.addAttribute("mostrarErrorContenidoMaterialTeoricoVacio", true);
				model.addAttribute("mensajeErrorContenidoMaterialTeoricoVacio", mensajeError);
				
				model.addAttribute("unidadForm", unidadForm);
				model.addAttribute("mostrarTabMaterialTeorico", true);
				model.addAttribute("mostrarTabVideo", false);
				model.addAttribute("mostrarTabPracticas", false);
				model.addAttribute("mostrarTabExamen", false);
				model.addAttribute("mostrarMensajeCantidadPreguntas", true);
				
				return DETALLE_CURSO;
			}			
		}
		
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
		
		if(!intentaLimpiar){
			byte[] materialBytes = unidadForm.getMaterialTeorico().getBytes();
			material.setMaterial(materialBytes);
			
			materialUnidadRepository.save(material);
		} else {
			materialUnidadRepository.delete(material.getId());
		}
		
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

	private boolean intentaLimpiarContenido(String materialTeorico, Unidad unidad) {		
		if(unidad.getMaterial() != null && !unidad.getMaterial().isEmpty()){
			MaterialUnidad materialUnidad = unidad.getMaterial().get(0);			
			if(materialUnidad.getMaterial() != null && materialUnidad.getMaterial().length > 0){
				boolean contenidoVacio = estaContenidoVacio(materialTeorico);
				//Contiene material y lo intenta limpiar
				if(contenidoVacio){
					return true;
				}				
			}
		}
		
		return false;
	}
	
	private boolean estaContenidoVacio2(String contenido){
		/*
		 * Si el contenido viene vacio, tiene la siguiente estructura:
		 * <p><br />
		 *	</p>
		 */
		
		//Esto deberia de hacerse con un parse de xhtml pero no me funciono y ya no tengo tiempo. Ver si pasa todas las pruebas, sino reveer esto.
		String contenidoInicial = "<p><br />";
		int indexInicio = contenido.indexOf(contenidoInicial);
		if(indexInicio == -1 || indexInicio != 0){
			return false;
		}
		
		int indexContenido = indexInicio + contenidoInicial.length();//En ese index deberia de comenzar el contenido real si escribio algo distinto de espacios.
		String contenidoFinal = "</p>";
		//Busco el primer contenidoFinal. Si no escribio nada, deberia de no tener nada mas.
		int indexFinal = contenido.indexOf(contenidoFinal);
		
		//Voy a verificar que no haya mas contenido xhtml que el que viene por defecto.
		int indexFinalExtra = contenido.length() - (indexFinal + contenidoFinal.length());		
		if(indexFinalExtra > 0){
			return false;
		}
		
		String contenidoEfectivo = contenido.substring(indexContenido, indexFinal);
		contenidoEfectivo = contenidoEfectivo.trim();//Saco los espacios en blanco al comienzo y al final
		
		if(contenidoEfectivo.length() > 0){
			return false;
		}
		
		return true;
	}
	
	private boolean estaContenidoVacio(String contenido){
		//org.jdom.input.SAXBuilder saxBuilder = new SAXBuilder();
		SAXBuilder saxBuilder = new SAXBuilder();
		try {
		    //org.jdom.Document doc = saxBuilder.build(new StringReader(xml));
			Document doc = saxBuilder.build(new StringReader(contenido));
		    String contenidoPuro = doc.getRootElement().getText();
		    contenidoPuro = contenidoPuro.trim();
		    System.out.println(contenidoPuro);
		    
		    if(contenidoPuro == null || contenidoPuro.isEmpty()){
		    	return true;
		    }
		} catch (JDOMException e) {
			System.out.println(e);
		    // handle JDOMException
		} catch (IOException e) {			
		    // handle IOException
			System.out.println(e);
		}
		
		return false;
	}

	private String validaContenidoMaterialUnidad(UnidadForm unidadForm) {
		String mensaje = null;
		String materialTeorico = unidadForm.getMaterialTeorico();
		if(materialTeorico == null || materialTeorico.isEmpty() || estaContenidoVacio(materialTeorico)){
			mensaje = "Debe de cargar material teórico para poder guardarlo.";
		} else {
			byte[] materialBytes = unidadForm.getMaterialTeorico().getBytes();
			if(materialBytes.length > ConstantesDelModelo.MAX_TAM_MATERIAL_TEORICO){
				mensaje = "El tamaño del material teorico debe ser menor a " + ConstantesDelModelo.MAX_TAM_MATERIAL_TEORICO + " " + ConstantesDelModelo.UNIDAD_TAM_MATERIAL_TEORICO;
			}
		}
		
		return mensaje;
	}
	
	@RequestMapping(value = "/guardarVideo", method = RequestMethod.POST)
	public String guardarVideo(@ModelAttribute @Valid UnidadForm unidadForm, BindingResult bindingResult, Model model) {
		if(unidadForm.getVideo().isEmpty()){
			model.addAttribute("mostrarMensajeWarningCargaVideo", true);
			model.addAttribute("mensajeWarningVideo", "No existe un video cargado para realizar la operación solicitada.");
			
			model.addAttribute("unidadForm", unidadForm);
			model.addAttribute("mostrarTabMaterialTeorico", false);
			model.addAttribute("mostrarTabVideo", true);
			model.addAttribute("mostrarTabPracticas", false);
			model.addAttribute("mostrarTabExamen", false);
			model.addAttribute("mostrarMensajeCantidadPreguntas", true);
			
			return DETALLE_CURSO;
		}
		
		boolean valida = validaVideo(unidadForm); 
		if(!valida){
			model.addAttribute("mostrarMensajeErrorValidacionCargaVideo", true);
			
			String base64 = null;
			try {
				base64 = Base64.encodeBase64String(unidadForm.getVideo().getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//data:video/mp4;base64,${video en base 64}
			String videoGuardado = "data:video/mp4;base64,";
			videoGuardado +=base64;
			
			unidadForm.setVideoBytes(videoGuardado);
			unidadForm.setEdicion(true);
			
			model.addAttribute("unidadForm", unidadForm);
			model.addAttribute("mostrarTabMaterialTeorico", false);
			model.addAttribute("mostrarTabVideo", true);
			model.addAttribute("mostrarTabPracticas", false);
			model.addAttribute("mostrarTabExamen", false);
			model.addAttribute("mostrarMensajeCantidadPreguntas", true);
			
			return DETALLE_CURSO;
		}
		
		Unidad unidad = unidadRepository.findOne(unidadForm.getUnidad().getId());//Voy a buscarla porque las listas se cargaron en otra sesion de hibernate (no en la actual).
		VideoUnidad video = null;
		if(unidad.getVideos() == null || unidad.getVideos().isEmpty()){
			video = new VideoUnidad();
			
			VideoUnidadId id = new VideoUnidadId();
			id.setIdCurso(unidadForm.getCurso().getId());
			id.setIdVideo(new Long(unidad.getId().getNumero()));
			id.setNumero(1);
			
			video.setId(id);
			unidad.addVideo(video);
			
			video.setUnidad(unidad);
			video.setIdCurso(unidad.getCurso().getId());
		} else {
			video = unidad.getVideos().get(0);
		}				
		
		boolean error = false;
		try {
			if(unidadForm.getVideo() != null){
				byte[] videoBytes = unidadForm.getVideo().getBytes();
				video.setVideo(videoBytes);
				video.setTitulo(unidadForm.getVideo().getOriginalFilename());
				
				videoUnidadRepository.save(video);
			}			
		} catch (IOException e) {
			e.printStackTrace();
			error = true;
		}  catch (Exception e) {
			e.printStackTrace();
			error = true;
		}
		
		String videoGuardado = null;
		String base64 = null;
		if(error){
			model.addAttribute("mostrarMensajeErrorCargaVideo", true);
			model.addAttribute("mensajeErrorVideo", "Se produjo un error al intentar guardar el video.");
			try {
				base64 = Base64.encodeBase64String(unidadForm.getVideo().getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Unidad unidadCompleta = unidadRepository.findOne(unidad.getId());		
			unidadForm.setUnidad(unidadCompleta);
			
			if(unidadCompleta.getVideos() != null && !unidadCompleta.getVideos().isEmpty()){
				base64 = Base64.encodeBase64String(unidadCompleta.getVideos().get(0).getVideo());
			}				
		}
		
		//data:video/mp4;base64,${video en base 64}
		videoGuardado = "data:video/mp4;base64,";
		videoGuardado +=base64;
		
		unidadForm.setVideoBytes(videoGuardado);
		unidadForm.setEdicion(true);
		model.addAttribute("unidadForm", unidadForm);
		model.addAttribute("mostrarTabMaterialTeorico", false);
		model.addAttribute("mostrarTabVideo", true);
		model.addAttribute("mostrarTabPracticas", false);
		model.addAttribute("mostrarTabExamen", false);
		model.addAttribute("mostrarMensajeCantidadPreguntas", true);
		model.addAttribute("mostrarMensajeMaterialGuardado", false);
		
		return DETALLE_CURSO;
	}
		
	private boolean validaVideo(UnidadForm unidadForm) {
		MultipartFile video = unidadForm.getVideo();
		if(video == null){
			return false;
		}
		
		boolean valida = true;
		List<String> errores = new ArrayList<String>();
		
		if(video.getOriginalFilename().length() > ConstantesDelModelo.MAX_VIDEO_NAME){
			errores.add("El nombre del video debe contener como máximo " + ConstantesDelModelo.MAX_VIDEO_NAME + " caracteres.");
			valida = false;
		}
		
		if(video.getSize() > ConstantesDelModelo.MAX_TAM_VIDEO){
			errores.add("El video debe ser menor a " + ConstantesDelModelo.MAX_TAM_VIDEO + " " + ConstantesDelModelo.UNIDAD_TAM_VIDEO);
			valida = false;
		}
		
		unidadForm.setErrores(errores);
		return valida;
	}
	
	@RequestMapping(value = "/eliminarVideo/{idCurso}/{unidadNro}", method = RequestMethod.GET)
	public String eliminarVideo(@PathVariable("idCurso") long idCurso, @PathVariable("unidadNro") int unidadNro, Model model) {
		ComponenteId idUnidad = new ComponenteId();
		idUnidad.setIdCurso(idCurso);
		idUnidad.setNumero(unidadNro);
		Unidad unidad = this.unidadRepository.findOne(idUnidad);
		boolean intentoEliminar = false;
		if(unidad.getVideos() == null || unidad.getVideos().isEmpty()){
			model.addAttribute("mostrarMensajeWarningCargaVideo", true);
			model.addAttribute("mensajeWarningVideo", "Nunca se guardó un video para poder ser eliminado.");		
		} else {
			//Como se carga un unico video, elimino directamente el que esta en el primer lugar
			VideoUnidad video = (unidad.getVideos() == null || unidad.getVideos().isEmpty() ) ? null : unidad.getVideos().get(0);
			if(video != null){
				unidad.setVideos(null);
				this.unidadRepository.save(unidad);
				this.videoUnidadRepository.delete(video);
				intentoEliminar = true;
			}
		}
		
		unidad = this.unidadRepository.findOne(idUnidad);		
		Curso curso = this.cursoService.encontrarCursoPorId(idCurso);
		UnidadForm unidadForm = new UnidadForm();
		unidadForm.setCurso(curso);
		unidadForm.setUnidad(unidad);
		unidadForm.setPublicado(false);
		
		String contenidoMaterialGuardado = null;
		if(unidad.getMaterial() != null && !unidad.getMaterial().isEmpty()){
			contenidoMaterialGuardado = new String(unidad.getMaterial().get(0).getMaterial());
		}		
		unidadForm.setMaterialTeorico(contenidoMaterialGuardado);
		
		if(Estado.PUBLICADO.equals(curso.getEstadoCurso())){
			unidadForm.setPublicado(true);
		}		
		
		if(intentoEliminar){
			String videoGuardado = null;
			if(unidad.getVideos() != null && !unidad.getVideos().isEmpty()){
				//Esto lo hago para verificar que el video se a eliminado correctamente de la unidad
				
				//data:video/webm;base64,${video en base 64}
				videoGuardado = "data:video/mp4;base64,";
				String base64 = Base64.encodeBase64String(unidad.getVideos().get(0).getVideo());
				videoGuardado +=base64;
				unidadForm.setVideoBytes(videoGuardado);
			} else {
				model.addAttribute("mostrarMensajeEliminarVideo", true);
			}
		}		
		
		unidadForm.setEdicion(true);		
		
		model.addAttribute("unidadForm", unidadForm);
		model.addAttribute("mostrarTabMaterialTeorico", false);
		model.addAttribute("mostrarTabVideo", true);
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
			id.setNumero(unidadForm.getUnidad().getId().getNumero());
			id.setIdExamen(1);
			
			examenUnidad.setId(id);
		}
		
		examenUnidad.setCantPreguntasUsuario(unidadForm.getCantidadPreguntasAlumno());
		
		List<PreguntaExamenUnidad> preguntas = null;
		if(unidadForm.getExamenUnidad()!=null){
			unidadForm.getExamenUnidad().getPreguntas();			
		}
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
		
		List<PreguntaExamenUnidad> preguntas = null;
		if(unidadForm.getExamenUnidad()!=null){
			unidadForm.getExamenUnidad().getPreguntas();			
		}
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
