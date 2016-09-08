package org.educa.core.api;

import java.util.List;

import org.educa.core.dao.CursoRepository;
import org.educa.core.entities.model.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/curso")
public class CursoApiController {

	@Autowired
	@Qualifier("cursoRepository")
	private CursoRepository cursoRepository;

	@RequestMapping(method = RequestMethod.GET, value = "listar")
	public List<Curso> listar() {
		List<Curso> cursos = (List<Curso>) cursoRepository.findAll();
		return cursos;
	}

	@RequestMapping(method = RequestMethod.GET, value = "listar/{categoriaId}")
	public List<Curso> listarPorCategoria(@PathVariable Long categoriaId) {
		List<Curso> cursos = (List<Curso>) cursoRepository.findByCategoriaId(categoriaId);
		return cursos;
	}

}
