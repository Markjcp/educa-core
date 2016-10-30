package org.educa.core.controller;

import java.util.Calendar;
import java.util.SortedSet;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.educa.core.bean.UsuarioSeguridad;
import org.educa.core.controller.forms.ForoForm;
import org.educa.core.dao.ComentarioRepository;
import org.educa.core.dao.ForoRepository;
import org.educa.core.dao.SesionRepository;
import org.educa.core.dao.TemaRepository;
import org.educa.core.entities.model.Comentario;
import org.educa.core.entities.model.ComponenteId;
import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.EstadoPublicacion;
import org.educa.core.entities.model.Foro;
import org.educa.core.entities.model.Sesion;
import org.educa.core.entities.model.Tema;
import org.educa.core.entities.model.Usuario;
import org.educa.core.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("foro")
public class ForoController {
	
	private static final String LISTADO_SESION_FORO = "views/curso/foro/listado-sesiones-foro";	
	private static final String DETALLE_TEMA = "views/curso/foro/detalle-tema";
	
	@Autowired
	private CursoService cursoService;
	
	@Autowired
	@Qualifier("sesionRepository")
	private SesionRepository sesionRepository;
	
	@Autowired
	@Qualifier("foroRepository")
	private ForoRepository foroRepository;
	
	@Autowired
	@Qualifier("temaRepository")
	private TemaRepository temaRepository;
	
	@Autowired
	@Qualifier("comentarioRepository")
	private ComentarioRepository comentarioRepository;

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
	
	@RequestMapping(value = "/agregarTema/{idCurso}/{nroSesion}", method = RequestMethod.POST)
	public String agregarTema(@PathVariable("idCurso") long idCurso, @PathVariable("nroSesion") int nroSesion, @ModelAttribute @Valid ForoForm foroForm, 
			BindingResult bindingResult, Model model, HttpServletRequest request) {		
		if (bindingResult.hasFieldErrors("tema.*")) {
// 			Esto lo comente porque no se si la idea era hacer lógica para que el popup no se cierre, se puede mejorar
//			SortedSet<Sesion> sesiones = sesionRepository.findByFechaAndIdCurso(Calendar.getInstance().getTime(), idCurso);
//			model.addAttribute("abrirPopupAltaTemaError", true);//TODO EDIAZ ESTO HAY Q HACERLO EN LA VISTA, NO ESTA ACTUALMENTE
//			model.addAttribute("foroForm", foroForm);
//			model.addAttribute("sesiones", sesiones);
			model.addAttribute("mensajeAltaTemaError", true);
			String errorMsj = bindingResult.getAllErrors().iterator().next().getDefaultMessage();
			model.addAttribute("mensajeAltaTemaErrorMsj", errorMsj);			
			cargarDatosListadoSesionForo(idCurso, new ForoForm(), nroSesion, model);
			
			return LISTADO_SESION_FORO;
		}
		
		//addTema
		ComponenteId id = new ComponenteId();
		id.setIdCurso(idCurso);
		id.setNumero(nroSesion);
		Sesion sesion = sesionRepository.findOne(id);
		if(sesion.getId() != null && sesion.getId().getIdCurso() == idCurso && sesion.getId().getNumero() == nroSesion){
			Foro foro = sesion.getForo();
			Tema tema = foroForm.getTema();
			//Tema creado por un profesor, por eso va en estado APROBADO
			tema.setEstado(EstadoPublicacion.APROBADO);
			tema.setFechaCreacion(Calendar.getInstance().getTime());
			tema.setIdForo(foro.getId());
			
			//Guardo los datos de usuario logueado
			Usuario usuarioLogeado = this.obtenerUsuarioLogueado(request);
			tema.setIdUsuario(usuarioLogeado == null ? null : usuarioLogeado.getId());
			tema.setUsuario(usuarioLogeado);
			
			foro.addTema(tema);
			foroRepository.save(foro);
			model.addAttribute("mensajeAltaTemaOk", true);//TODO EDIAZ ESTO HAY Q MOSTRARLO EN LA VISTA
		}
		
		cargarDatosListadoSesionForo(idCurso, new ForoForm(), nroSesion, model);
		
		return LISTADO_SESION_FORO;
	}	
	
	@RequestMapping(value = "/detalleTema/{idCurso}/{nroSesion}/{idTema}", method = RequestMethod.GET)
	public String detalleTema(@PathVariable("idCurso") long idCurso, @PathVariable("nroSesion") int nroSesion, @PathVariable("idTema") long idTema, Model model) {		
		cargarDatosDetalleTema(idTema, model, idCurso, nroSesion);
		
		return DETALLE_TEMA;
	}
	
	@RequestMapping(value = "/aprobarTema/{idCurso}/{nroSesion}/{idTema}", method = RequestMethod.GET)
	public String aprobarTema(@PathVariable("idCurso") long idCurso, @PathVariable("nroSesion") int nroSesion, @PathVariable("idTema") long idTema,
			@ModelAttribute @Valid ForoForm foroForm, BindingResult bindingResult, Model model) {
		Tema tema = temaRepository.findOne(idTema);
		Foro foro = foroRepository.findOne(tema.getIdForo());
		tema.setEstado(EstadoPublicacion.APROBADO);
		temaRepository.save(tema);
		foro.setCantidadTemasAprobados(foro.getCantidadTemasAprobados()+1);
		foro.setCantidadTemasPorAprobar(foro.getCantidadTemasPorAprobar()-1);
		foroRepository.save(foro);
		
		tema = temaRepository.findOne(idTema);//Lo vuelvo a buscar para que este actualizado
		model.addAttribute("tema", tema);
		
		cargarDatosListadoSesionForo(idCurso, new ForoForm(), nroSesion, model);
		
		return LISTADO_SESION_FORO;
	}
	
	@RequestMapping(value = "/ocultarTema/{idCurso}/{nroSesion}/{idTema}", method = RequestMethod.GET)
	public String ocultarTema(@PathVariable("idCurso") long idCurso, @PathVariable("nroSesion") int nroSesion, @PathVariable("idTema") long idTema,
			@ModelAttribute @Valid ForoForm foroForm, BindingResult bindingResult, Model model) {		
		Tema tema = temaRepository.findOne(idTema);
		tema.setEstado(EstadoPublicacion.RECHAZADO);
		temaRepository.save(tema);
		
		tema = temaRepository.findOne(idTema);//Lo vuelvo a buscar para que este actualizado
		model.addAttribute("tema", tema);
				
		cargarDatosListadoSesionForo(idCurso, new ForoForm(), nroSesion, model);
		
		return LISTADO_SESION_FORO;
	}
	
	@RequestMapping(value = "/aprobarComentario/{idCurso}/{nroSesion}/{idTema}/{idComentario}", method = RequestMethod.GET)
	public String aprobarComentario(@PathVariable("idCurso") long idCurso, @PathVariable("nroSesion") int nroSesion, @PathVariable("idTema") long idTema, 
			@PathVariable("idComentario") long idComentario, Model model) {		
		return cambiarEstadoComentario(idCurso, nroSesion, idTema, idComentario, model, EstadoPublicacion.APROBADO);
	}
	
	@RequestMapping(value = "/ocultarComentario/{idCurso}/{nroSesion}/{idTema}/{idComentario}", method = RequestMethod.GET)
	public String ocultarComentario(@PathVariable("idCurso") long idCurso, @PathVariable("nroSesion") int nroSesion, @PathVariable("idTema") long idTema, 
			@PathVariable("idComentario") long idComentario, Model model) {
		return cambiarEstadoComentario(idCurso, nroSesion, idTema, idComentario, model, EstadoPublicacion.RECHAZADO);
	}
	
	@RequestMapping(value = "/agregarComentario/{idCurso}/{nroSesion}/{idTema}", method = RequestMethod.POST)
	public String agregarComentario(@PathVariable("idCurso") long idCurso, @PathVariable("nroSesion") int nroSesion, @PathVariable("idTema") long idTema, 
			@ModelAttribute @Valid ForoForm foroForm, BindingResult bindingResult, Model model, HttpServletRequest request) {
		if (bindingResult.hasFieldErrors("comentario.*")) {			
			model.addAttribute("altaComentarioError", true);//TODO EDIAZ ESTO HAY Q HACERLO EN LA VISTA, NO ESTA ACTUALMENTE
			String errorMsj = bindingResult.getAllErrors().iterator().next().getDefaultMessage();
			model.addAttribute("altaComentarioErrorMsj", errorMsj);
			cargarDatosDetalleTema(idTema, model, idCurso, nroSesion);
			
			return DETALLE_TEMA;
		}
		
		Comentario comentario = foroForm.getComentario();
		comentario.setEstado(EstadoPublicacion.APROBADO);//Lo creo el profesor asi que se publica directamente
		comentario.setFechaCreacion(Calendar.getInstance().getTime());
		comentario.setIdTema(idTema);
		Tema tema = this.temaRepository.findOne(idTema);
		comentario.setTema(tema);
		//Guardo los datos de usuario logueado
		Usuario usuarioLogeado = this.obtenerUsuarioLogueado(request);
		comentario.setIdUsuario(usuarioLogeado == null ? null : usuarioLogeado.getId());
		comentario.setUsuario(usuarioLogeado);
		comentario = comentarioRepository.save(comentario);
		//TODO mucha de las cosas que sigue se pueden refactorizar xq se repiten en otra parte pero ahora queda asi
		
		/*
		 * Si bien este metodo se puede hacer directo buscando el comentario y persistiendo en la base de una,
		 * lo hago asi porque necesito informacion de las cantidades, evitando tener que iterar varias veces las
		 * mismas colecciones.
		 */
		ComponenteId idSesion = new ComponenteId();
		idSesion.setIdCurso(idCurso);
		idSesion.setNumero(nroSesion);
		Sesion sesion = this.sesionRepository.findOne(idSesion);
		if(sesion == null){
			return DETALLE_TEMA;
		}
		
		Foro foro = sesion.getForo();
		if(foro == null){
			return DETALLE_TEMA;
		}
		
		//Busco el tema
		Tema temaActualizar = null;
		for(Tema unTema : foro.getTemas()){
			if(unTema.getId() == idTema){
				temaActualizar = unTema;
				break;
			}
		}
		
		if(temaActualizar == null){
			return DETALLE_TEMA;
		}
					
		temaActualizar.addComentario(comentario);
		foro.actualizarTema(temaActualizar);
		foro = this.foroRepository.save(foro);
		
		cargarDatosDetalleTema(idTema, model, idCurso, nroSesion);
		model.addAttribute("altaComentarioOk", true);//TODO EDIAZ ESTO HAY Q HACERLO EN LA VISTA, NO ESTA ACTUALMENTE
		
		return DETALLE_TEMA;
	}
	
	private void cargarDatosDetalleTema(long idTema, Model model, long idCurso, int nroSesion){
		Tema tema = temaRepository.findOne(idTema);
		model.addAttribute("tema", tema);
		
		Curso curso = this.cursoService.encontrarCursoPorId(idCurso);
		ForoForm foroForm = new ForoForm();
		foroForm.setCurso(curso);
		
		ComponenteId idSesion = new ComponenteId();
		idSesion.setIdCurso(idCurso);
		idSesion.setNumero(nroSesion);
		Sesion sesion = this.sesionRepository.findOne(idSesion);		
		foroForm.setSesion(sesion);
		
		model.addAttribute("foroForm", foroForm);
	}
	
	public String cambiarEstadoComentario(long idCurso, int nroSesion, long idTema, long idComentario, Model model, EstadoPublicacion estado) {		
		/*
		 * Si bien este metodo se puede hacer directo buscando el comentario y persistiendo en la base de una,
		 * lo hago asi porque necesito informacion de las cantidades, evitando tener que iterar varias veces las
		 * mismas colecciones.
		 */
		ComponenteId idSesion = new ComponenteId();
		idSesion.setIdCurso(idCurso);
		idSesion.setNumero(nroSesion);
		Sesion sesion = this.sesionRepository.findOne(idSesion);
		if(sesion == null){
			return DETALLE_TEMA;
		}
		
		Foro foro = sesion.getForo();
		if(foro == null){
			return DETALLE_TEMA;
		}
		
		//Busco el tema
		Tema temaActualizar = null;
		for(Tema unTema : foro.getTemas()){
			if(unTema.getId() == idTema){
				temaActualizar = unTema;
				break;
			}
		}
		
		if(temaActualizar == null){
			return DETALLE_TEMA;
		}
		
		//Busco comentario
		Comentario comentarioActualizado = null;
		for(Comentario comentario : temaActualizar.getComentarios()){
			if(comentario.getId() == idComentario){
				comentarioActualizado = comentario;
				break;
			}
		}
		
		comentarioActualizado.setEstado(estado);		
		temaActualizar.addComentario(comentarioActualizado);
		foro.actualizarTema(temaActualizar);
		if(estado.equals(EstadoPublicacion.APROBADO)){
			foro.setCantidadComentariosAprobados(foro.getCantidadComentariosAprobados()+1);
			foro.setCantidadComentariosPorAprobar(foro.getCantidadComentariosPorAprobar()-1);
		}
		foro = this.foroRepository.save(foro);
		
		cargarDatosDetalleTema(idTema, model, idCurso, nroSesion);
		
		return DETALLE_TEMA;
	}
	
	private void cargarDatosListadoSesionForo(long idCurso, ForoForm foroForm, int nroSesion, Model model){
		Curso curso = this.cursoService.encontrarCursoPorId(idCurso);
		foroForm.setCurso(curso);
		
		ComponenteId idSesion = new ComponenteId();
		idSesion.setIdCurso(idCurso);
		idSesion.setNumero(nroSesion);
		Sesion sesion = this.sesionRepository.findOne(idSesion);		
		foroForm.setSesion(sesion);
		
		model.addAttribute("foroForm", foroForm);
		
		SortedSet<Sesion> sesiones = sesionRepository.findByFechaAndIdCurso(Calendar.getInstance().getTime(), idCurso);
		model.addAttribute("sesiones", sesiones);
	}
	
	private Usuario obtenerUsuarioLogueado(HttpServletRequest request){
		SecurityContext contexto = (SecurityContext)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		Usuario usuarioLogeado = null;
		if(contexto != null){
			usuarioLogeado = contexto.getAuthentication() == null ? null : (((UsuarioSeguridad)contexto.getAuthentication().getPrincipal()).getUsuario());
		}
		
		return usuarioLogeado;
	}
}
