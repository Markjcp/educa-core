package org.educa.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.Estado;
import org.educa.core.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("docente")
public class DocenteController {
	
	private static final String BANDEJA_CURSO = "views/docente/bandeja-cursos";
	private static final String MODELO_ATRIBUTO_CURSOS = "cursos";
	private static final int PRODUCT_LIST_PAGE_SIZE = 5;
	
	@Autowired
	private CursoService cursoService;
	
	@RequestMapping(value="/{id}/bandeja-cursos", method = RequestMethod.GET)
	public String misCursos(HttpServletRequest request, 
				@PathVariable("id") long idUsuario, 
				Model model,
				RedirectAttributes redirectAttributes) {
		
		request.getSession().setAttribute("listaCursos", null);
		String nombreCurso = request.getParameter("nombreCurso");
		if (nombreCurso != null) {
			redirectAttributes.addFlashAttribute("nombreCurso", nombreCurso);
		}
		return "redirect:/docente/" + idUsuario + "/bandeja-cursos/1";
	}
	
	@RequestMapping(value="/{id}/bandeja-cursos/{numeroPagina}", method = RequestMethod.GET)
	public String misCursosPagina(HttpServletRequest request, 
			@PathVariable("id") long legajo,
			Model model,
			@PathVariable Integer numeroPagina, 
			Model uiModel) {
		
		return bandejaPorPagina(request, legajo, model, numeroPagina, uiModel);
	}
	
	@RequestMapping(value = "/cambiarEstadoPublicacion/{idCurso}/{pagina}/{idDocente}", method = RequestMethod.POST)
	public String cambiarEstadoPublicacion(@PathVariable("idCurso") long idCurso, @PathVariable("pagina") Integer numeroPagina, 
			@PathVariable("idDocente") long legajo, Model model, HttpServletRequest request) {
		/*
		 * OjO: Viene con el valor con el que fue en el formulario en la accion anterior, 
		 * asi que hay que cambiarle el valor.
		 */
		Curso curso = cursoService.encontrarCursoPorId(idCurso);
		Estado nuevoEstadoCurso = Estado.NO_PUBLICADO;		
		if(!curso.isPublicado()){
			nuevoEstadoCurso = Estado.PUBLICADO;
		}
		
		if(Estado.PUBLICADO.equals(nuevoEstadoCurso) && (curso.getUnidades() == null || curso.getUnidades().isEmpty())){
			model.addAttribute("mostrarMensajeErrorPublicacion", true);
			StringBuilder mensaje = new StringBuilder();
			mensaje.append("El curso '" + curso.getNombre() + "' ");
			mensaje.append("no se puede publicar porque no tiene unidades cargadas.");					
			model.addAttribute("mensajeErrorPublicacion", mensaje.toString());
			
			return bandejaPorPagina(request, legajo, model, numeroPagina, model);
		}
			
		curso.setEstadoCurso(nuevoEstadoCurso);		
		cursoService.guardarCurso(curso);
		
		request.getSession().setAttribute("listaCursos", null);
		
		return bandejaPorPagina(request, legajo, model, numeroPagina, model);
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	private String bandejaPorPagina(HttpServletRequest request, @PathVariable("id") long legajo, Model model,
			@PathVariable Integer numeroPagina, Model uiModel){
		String nombreCurso = (String)model.asMap().get("nombreCurso");
		if (nombreCurso == null) {
			nombreCurso = "";
		}

		PagedListHolder<Curso> pagedListHolder = 
			(PagedListHolder<Curso>)request.getSession().getAttribute("listaCursos");
		if (pagedListHolder == null) {
			if (!nombreCurso.equals("")) {
				pagedListHolder = new PagedListHolder(cursoService.obtenerCursosDocente(legajo, nombreCurso));
			} else {
				pagedListHolder = new PagedListHolder(cursoService.obtenerCursosDocente(legajo));
			}
			pagedListHolder.setPageSize(PRODUCT_LIST_PAGE_SIZE);
		} else {
			final int irAPagina = numeroPagina - 1;
			if (irAPagina <= pagedListHolder.getPageCount() && irAPagina >= 0) {
				pagedListHolder.setPage(irAPagina);				
			}
		}
		
		request.getSession().setAttribute("listaCursos", pagedListHolder);
				
		int current = pagedListHolder.getPage() + 1;
		int begin = Math.max(1, current - PRODUCT_LIST_PAGE_SIZE);
		int end = Math.min(begin+5, pagedListHolder.getPageCount());
		int totalPageCount = pagedListHolder.getPageCount();
		String baseUrl = "/docente/" + legajo +"/bandeja-cursos/";
		
		uiModel.addAttribute("legajo", legajo);
		uiModel.addAttribute("nombreCurso", nombreCurso);
		uiModel.addAttribute("beginIndex", begin);
		uiModel.addAttribute("endIndex", end);
		uiModel.addAttribute("currentIndex", current);
		uiModel.addAttribute("totalPageCount", totalPageCount);
		uiModel.addAttribute("baseUrl", baseUrl);
		uiModel.addAttribute(MODELO_ATRIBUTO_CURSOS, pagedListHolder);
		
		return BANDEJA_CURSO;
	}
}
