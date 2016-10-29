package org.educa.core.controller;

import java.util.Calendar;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.validation.Valid;

import org.educa.core.controller.forms.ForoForm;
import org.educa.core.dao.ForoRepository;
import org.educa.core.dao.SesionRepository;
import org.educa.core.entities.model.Comentario;
import org.educa.core.entities.model.ComponenteId;
import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.EstadoForo;
import org.educa.core.entities.model.EstadoPublicacion;
import org.educa.core.entities.model.Foro;
import org.educa.core.entities.model.Sesion;
import org.educa.core.entities.model.Tema;
import org.educa.core.entities.model.Usuario;
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
	public String agregarTema(@PathVariable("idCurso") long idCurso, @PathVariable("nroSesion") int nroSesion, @ModelAttribute @Valid ForoForm foroForm, BindingResult bindingResult, Model model) {
		//TODO
		System.out.println("ENTRO A DAR AGREGAR UN TEMA");
		SortedSet<Sesion> sesiones = sesionRepository.findByFechaAndIdCurso(Calendar.getInstance().getTime(), idCurso);
		
		if (bindingResult.hasFieldErrors("tema.*")) {			
			model.addAttribute("abrirPopupAltaTemaError", true);//TODO EDIAZ ESTO HAY Q HACERLO EN LA VISTA, NO ESTA ACTUALMENTE
			model.addAttribute("foroForm", foroForm);
			model.addAttribute("sesiones", sesiones);
			
			return LISTADO_SESION_FORO;
		}
		
		//addTema
		for(Sesion sesion : sesiones){
			if(sesion.getId() != null && sesion.getId().getIdCurso() == idCurso && sesion.getId().getNumero() == nroSesion){
				Foro foro = sesion.getForo();
				Tema tema = foroForm.getTema();
				//Tema creado por un profesor, por eso va en estado APROBADO
				tema.setEstado(EstadoPublicacion.APROBADO);
				tema.setFechaCreacion(Calendar.getInstance().getTime());
				tema.setIdForo(foro.getId());
				
//				//Guardo los datos de usuario logueado
//				SecurityContext contexto = 
//						(SecurityContext)request.getSession().getAttribute("ACEGI_SECURITY_CONTEXT");
//						 
//						String login = ((org.acegisecurity.userdetails.User)
//						                            (context.getAuthentication().getPrincipal())).getUsername();
//				tema.setIdUsuario(   );
				
				//foro.addTema(foroForm.a);
			}
		}
		
		return LISTADO_SESION_FORO;
	}	
	
	@RequestMapping(value = "/detalleTema/{idCurso}/{nroSesion}/{idTema}", method = RequestMethod.GET)
	public String detalleTema(@PathVariable("idCurso") long idCurso, @PathVariable("nroSesion") int nroSesion, @PathVariable("idTema") long idTema, Model model) {
		//TODO HAY Q HACERLO!
		System.out.println("ENTRO PARA VER EL DETALLE DE UN TEMA");
		
		Tema tema = new Tema();
		tema.setTitulo("Tema de prueba");
		tema.setDescripcion("Esto es una descripcion para el tema 1 de prueba.");
		tema.setId(1l);
		
		SortedSet<Comentario> comentarios = new TreeSet<Comentario>();
		Comentario comentario = new Comentario();
		comentario.setId(1l);
		comentario.setDescripcion("Comentario 1");
		comentario.setFechaCreacion(Calendar.getInstance().getTime());
		comentario.setEstado(EstadoPublicacion.APROBADO);
		Usuario usuario = new Usuario();
		usuario.setId(1l);
		usuario.setNombre("Pepe");
		usuario.setApellido("Sanchez");
		comentario.setUsuario(usuario);
		comentarios.add(comentario);
		
		Comentario comentario2 = new Comentario();
		comentario2.setId(1l);
		comentario2.setDescripcion("Comentario 2");
		comentario2.setFechaCreacion(Calendar.getInstance().getTime());
		comentario2.setEstado(EstadoPublicacion.RECHAZADO);
		Usuario usuario2 = new Usuario();
		usuario2.setId(2l);
		usuario2.setNombre("Andres");
		usuario2.setApellido("Tata");
		comentario2.setUsuario(usuario2);
		comentarios.add(comentario2);
		
		Comentario comentario3 = new Comentario();
		comentario3.setId(1l);
		comentario3.setDescripcion("Comentario 3");
		comentario3.setFechaCreacion(Calendar.getInstance().getTime());
		comentario3.setEstado(EstadoPublicacion.INDEFINIDO);
		Usuario usuario3 = new Usuario();
		usuario3.setId(3l);
		usuario3.setNombre("Carla");
		usuario3.setApellido("Mendez");
		comentario3.setUsuario(usuario3);
		comentarios.add(comentario3);
		tema.setComentarios(comentarios);
		
		tema.setEstado(EstadoPublicacion.APROBADO);
		
		model.addAttribute("tema", tema);
		
		//TODO en el form hay q mandar el curso y la sesion
		Curso curso = this.cursoService.encontrarCursoPorId(idCurso);
		ForoForm foroForm = new ForoForm();
		foroForm.setCurso(curso);
		ComponenteId idSesion = new ComponenteId();
		idSesion.setIdCurso(idCurso);
		idSesion.setNumero(nroSesion);
		Sesion sesion = this.sesionRepository.findOne(idSesion);
		
		if(sesion.getForo() == null){
			Foro foro = new Foro();
			SortedSet<Tema> temas = new TreeSet<Tema>();
			temas.add(tema);
			foro.setTemas(temas);
			foro.setEstado(EstadoForo.MODERADO);
			sesion.setForo(foro);
		}
		
		foroForm.setSesion(sesion);
		
		model.addAttribute("foroForm", foroForm);
		
		return DETALLE_TEMA;
	}
	
	@RequestMapping(value = "/aprobarTema/{idCurso}/{nroSesion}/{idTema}", method = RequestMethod.GET)
	public String aprobarTema(@PathVariable("idCurso") long idCurso, @PathVariable("nroSesion") int nroSesion, @PathVariable("idTema") long idTema,
			@ModelAttribute @Valid ForoForm foroForm, BindingResult bindingResult, Model model) {
		//TODO
		System.out.println("ENTRO A DAR APROBAR UN TEMA");
		
		return LISTADO_SESION_FORO;
	}
	
	@RequestMapping(value = "/aprobarComentario/{idCurso}/{nroSesion}/{idTema}/{idComentario}", method = RequestMethod.POST)
	public String aprobarComentario(@PathVariable("idCurso") long idCurso, @PathVariable("nroSesion") int nroSesion, @PathVariable("idTema") long idTema, 
			@PathVariable("idComentario") long idComentario, Model model) {
		//TODO
		System.out.println("ENTRO A APROBAR UN COMENTARIO");
		
		return DETALLE_TEMA;
	}
	
	@RequestMapping(value = "/ocultarComentario/{idCurso}/{nroSesion}/{idTema}/{idComentario}", method = RequestMethod.POST)
	public String ocultarComentario(@PathVariable("idCurso") long idCurso, @PathVariable("nroSesion") int nroSesion, @PathVariable("idTema") long idTema, 
			@PathVariable("idComentario") long idComentario, Model model) {
		//TODO
		System.out.println("ENTRO A OCULTAR UN COMENTARIO");
		
		return DETALLE_TEMA;
	}
	
	@RequestMapping(value = "/agregarComentario/{idCurso}/{nroSesion}/{idTema}", method = RequestMethod.POST)
	public String agregarComentario(@PathVariable("idCurso") long idCurso, @PathVariable("nroSesion") int nroSesion, @PathVariable("idTema") long idTema, 
			@ModelAttribute @Valid ForoForm foroForm, BindingResult bindingResult, Model model) {
		//TODO
		System.out.println("ENTRO A DAR DE ALTA UN COMENTARIO");
		
		return LISTADO_SESION_FORO;
	}
}
