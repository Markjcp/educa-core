package org.educa.core.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
import org.springframework.beans.support.PagedListHolder;
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
	private static final int COMENTS_LIST_PAGE_SIZE = 10;
	
	
	
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
			model.addAttribute("abrirPopupAltaTemaError", true);
			model.addAttribute("nroSesion", nroSesion);
			cargarDatosListadoSesionForo(idCurso, foroForm, nroSesion, model);
			
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
			foro.setCantidadTemasAprobados(foro.getCantidadTemasAprobados() + 1);
			foroRepository.save(foro);
			model.addAttribute("mensajeAltaTemaOk", true);
		}
		
		cargarDatosListadoSesionForo(idCurso, new ForoForm(), nroSesion, model);
		
		return LISTADO_SESION_FORO;
	}	
	
	@RequestMapping(value = "/detalleTema/{idCurso}/{nroSesion}/{idTema}", method = RequestMethod.GET)
	public String detalleTema(HttpServletRequest request, @PathVariable("idCurso") long idCurso, @PathVariable("nroSesion") int nroSesion, @PathVariable("idTema") long idTema, Model model) {		
		cargarDatosDetalleTema(idTema, model, idCurso, nroSesion, new ForoForm());

		request.getSession().setAttribute("listaComentarios", null);
		armarPaginadoComentarios(request, idCurso, nroSesion, idTema, 1, model);

		return DETALLE_TEMA;
	}
	
	@RequestMapping(value = "/detalleTema/{idCurso}/{nroSesion}/{idTema}/{numeroPagina}", method = RequestMethod.GET)
	public String detalleTema(HttpServletRequest request, @PathVariable("idCurso") long idCurso, @PathVariable("nroSesion") int nroSesion, 
								@PathVariable("idTema") long idTema, @PathVariable("numeroPagina") int numeroPagina, Model model) {
		
		cargarDatosDetalleTema(idTema, model, idCurso, nroSesion, new ForoForm());		
		armarPaginadoComentarios(request, idCurso, nroSesion, idTema, numeroPagina, model);
		
		return DETALLE_TEMA;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void armarPaginadoComentarios(HttpServletRequest request, long idCurso, int nroSesion, long idTema,
			int numeroPagina, Model model) {
		PagedListHolder<Comentario> pagedListHolder = 
				(PagedListHolder<Comentario>)request.getSession().getAttribute("listaComentarios");
		
		if (pagedListHolder == null) {			
			Tema tema = temaRepository.findOne(idTema);			
			List<Comentario> comentarios = new ArrayList<Comentario>();
			comentarios.addAll(tema.getComentarios());
			
			pagedListHolder =  new PagedListHolder(comentarios);
			pagedListHolder.setPageSize(COMENTS_LIST_PAGE_SIZE);
			
			request.getSession().setAttribute("listaComentarios", pagedListHolder);
		} else {
			final int irAPagina = numeroPagina - 1;
			if (irAPagina <= pagedListHolder.getPageCount() && irAPagina >= 0) {
				pagedListHolder.setPage(irAPagina);				
			}
		}	
		
		int current = pagedListHolder.getPage() + 1;
		int begin = Math.max(1, current - COMENTS_LIST_PAGE_SIZE);
		int end = Math.min(begin+COMENTS_LIST_PAGE_SIZE, pagedListHolder.getPageCount());
		int totalPageCount = pagedListHolder.getPageCount();
		String baseUrl = "/foro/detalleTema/"+idCurso+"/"+nroSesion+"/"+idTema+"/";
				
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("comentarios", pagedListHolder);
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
		EstadoPublicacion estadoAnterior = tema.getEstado();
		tema.setEstado(EstadoPublicacion.RECHAZADO);
		temaRepository.save(tema);
		
		//Voy a actualizar las cantidades de temas
		Foro foro = foroRepository.findOne(tema.getIdForo());
		if(foro.isModerado()){
			if(EstadoPublicacion.APROBADO.equals(estadoAnterior)){
				foro.setCantidadTemasAprobados(foro.getCantidadTemasAprobados() - 1);
			} else if (!EstadoPublicacion.RECHAZADO.equals(estadoAnterior)){
				foro.setCantidadTemasPorAprobar(foro.getCantidadTemasPorAprobar() - 1);
			}
		} else {
			//Solo estan en estado aprobado o rechazado
			if(!EstadoPublicacion.RECHAZADO.equals(estadoAnterior)){
				foro.setCantidadTemasAprobados(foro.getCantidadTemasAprobados() - 1);
			}
		}
		foroRepository.save(foro);
		
		tema = temaRepository.findOne(idTema);//Lo vuelvo a buscar para que este actualizado
		model.addAttribute("tema", tema);
				
		cargarDatosListadoSesionForo(idCurso, new ForoForm(), nroSesion, model);
		
		return LISTADO_SESION_FORO;
	}
	
	@RequestMapping(value = "/aprobarComentario/{idCurso}/{nroSesion}/{idTema}/{idComentario}", method = RequestMethod.GET)
	public String aprobarComentario(@PathVariable("idCurso") long idCurso, @PathVariable("nroSesion") int nroSesion, @PathVariable("idTema") long idTema, 
			@PathVariable("idComentario") long idComentario, Model model, HttpServletRequest request) {
		
		String nombreVista = cambiarEstadoComentario(idCurso, nroSesion, idTema, idComentario, model, EstadoPublicacion.APROBADO);
		request.getSession().setAttribute("listaComentarios", null);
		armarPaginadoComentarios(request, idCurso, nroSesion, idTema, 1, model);	
		return nombreVista;
	}
	
	@RequestMapping(value = "/ocultarComentario/{idCurso}/{nroSesion}/{idTema}/{idComentario}", method = RequestMethod.GET)
	public String ocultarComentario(@PathVariable("idCurso") long idCurso, @PathVariable("nroSesion") int nroSesion, @PathVariable("idTema") long idTema, 
			@PathVariable("idComentario") long idComentario, Model model,  HttpServletRequest request) {
		String nombreVista = cambiarEstadoComentario(idCurso, nroSesion, idTema, idComentario, model, EstadoPublicacion.RECHAZADO);
		request.getSession().setAttribute("listaComentarios", null);
		armarPaginadoComentarios(request, idCurso, nroSesion, idTema, 1, model);
		return nombreVista;
	}
	
	@RequestMapping(value = "/agregarComentario/{idCurso}/{nroSesion}/{idTema}", method = RequestMethod.POST)
	public String agregarComentario(@PathVariable("idCurso") long idCurso, @PathVariable("nroSesion") int nroSesion, @PathVariable("idTema") long idTema, 
			@ModelAttribute @Valid ForoForm foroForm, BindingResult bindingResult, Model model, HttpServletRequest request) {
		if (bindingResult.hasFieldErrors("comentario.*")) {			
			model.addAttribute("altaComentarioError", true);
			String errorMsj = bindingResult.getAllErrors().iterator().next().getDefaultMessage();
			model.addAttribute("altaComentarioErrorMsj", errorMsj);
			cargarDatosDetalleTema(idTema, model, idCurso, nroSesion, foroForm);
			armarPaginadoComentarios(request, idCurso, nroSesion, idTema, 1, model);
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
			armarPaginadoComentarios(request, idCurso, nroSesion, idTema, 1, model);
			return DETALLE_TEMA;
		}
		
		Foro foro = sesion.getForo();
		if(foro == null){
			armarPaginadoComentarios(request, idCurso, nroSesion, idTema, 1, model);
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
			armarPaginadoComentarios(request, idCurso, nroSesion, idTema, 1, model);
			return DETALLE_TEMA;
		}
					
		temaActualizar.addComentario(comentario);
		foro.actualizarTema(temaActualizar);
		foro.setCantidadComentariosAprobados(foro.getCantidadComentariosAprobados() + 1);
		foro = this.foroRepository.save(foro);
		
		cargarDatosDetalleTema(idTema, model, idCurso, nroSesion, new ForoForm());
		model.addAttribute("altaComentarioOk", true);
		
		request.getSession().setAttribute("listaComentarios", null);
		armarPaginadoComentarios(request, idCurso, nroSesion, idTema, 1, model);
		return DETALLE_TEMA;
	}
	
	private void cargarDatosDetalleTema(long idTema, Model model, long idCurso, int nroSesion, ForoForm foroForm){
		Tema tema = temaRepository.findOne(idTema);
		model.addAttribute("tema", tema);			
		
		Curso curso = this.cursoService.encontrarCursoPorId(idCurso);
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
		
		EstadoPublicacion estadoAnterior = comentarioActualizado.getEstado();
		comentarioActualizado.setEstado(estado);		
		temaActualizar.addComentario(comentarioActualizado);
		foro.actualizarTema(temaActualizar);
		if(estado.equals(EstadoPublicacion.APROBADO)){		
			foro.setCantidadComentariosAprobados(foro.getCantidadComentariosAprobados()+1);
			if(foro.isModerado()){
				temaActualizar.setCantidadComentariosPorAprobar(temaActualizar.getCantidadComentariosPorAprobar() - 1);
			}
		}
		
		if(EstadoPublicacion.RECHAZADO.equals(estado)){
			if(foro.isModerado()){
				if(EstadoPublicacion.APROBADO.equals(estadoAnterior)){
					foro.setCantidadComentariosAprobados(foro.getCantidadComentariosAprobados() - 1);
				} else if (!EstadoPublicacion.RECHAZADO.equals(estadoAnterior)){
					foro.setCantidadComentariosPorAprobar(foro.getCantidadComentariosPorAprobar() - 1);
				}
			} else {
				//Solo estan en estado aprobado o rechazado
				if(!EstadoPublicacion.RECHAZADO.equals(estadoAnterior)){
					foro.setCantidadComentariosAprobados(foro.getCantidadComentariosAprobados() - 1);
				}
			}
		}
		
		foroRepository.save(foro);		
		
		//En ambos se actualizan las cantidades
		this.temaRepository.save(temaActualizar);
		
		cargarDatosDetalleTema(idTema, model, idCurso, nroSesion, new ForoForm());
		
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
