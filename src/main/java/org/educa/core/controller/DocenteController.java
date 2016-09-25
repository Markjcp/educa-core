package org.educa.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.educa.core.entities.model.Curso;
import org.educa.core.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("docente")
public class DocenteController {
	
	private static final String BANDEJA_CURSO = "views/docente/bandeja-cursos";
	private static final String MODELO_ATRIBUTO_CURSOS = "cursos";
	private static final String PRODUCT_LIST_BASEURL = "views/docente/bandeja-cursos";
	private static final int PRODUCT_LIST_PAGE_SIZE = 5;
	
	@Autowired
	private CursoService cursoService;
	
	@RequestMapping(value="/{id}/bandeja-cursos", method = RequestMethod.GET)
	public String misCursos(HttpServletRequest request, @PathVariable("id") long idUsuario, Model model) {
		request.getSession().setAttribute("listaCursos", null);
		

		return "redirect:/" + idUsuario + "/bandeja-cursos/1";
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	@RequestMapping(value="/{id}/bandeja-cursos/{numeroPagina}", method = RequestMethod.GET)
	public String misCursosPagina(HttpServletRequest request, 
			@PathVariable Integer numeroPagina, 
			Model uiModel) {

		
		PagedListHolder<?> pagedListHolder = 
			(PagedListHolder<?>)request.getSession().getAttribute("listaCursos");
		
		if (pagedListHolder == null) {
			
			pagedListHolder = new PagedListHolder(cursoService.obtenerTodos());
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
		String baseUrl = PRODUCT_LIST_BASEURL;
		
		uiModel.addAttribute("beginIndex", begin);
		uiModel.addAttribute("endIndex", end);
		uiModel.addAttribute("currentIntex", current);
		uiModel.addAttribute("totalPageCount", totalPageCount);
		uiModel.addAttribute("baseUrl", baseUrl);
		uiModel.addAttribute(MODELO_ATRIBUTO_CURSOS, pagedListHolder);
		
		return BANDEJA_CURSO;

	}
	
	
	
	
}
