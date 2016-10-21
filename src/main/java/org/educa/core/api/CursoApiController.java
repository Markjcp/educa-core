package org.educa.core.api;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.educa.core.dao.CursoDao;
import org.educa.core.dao.CursoRepository;
import org.educa.core.dao.ParametroRepository;
import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.Estado;
import org.educa.core.entities.model.Parametro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/curso")
public class CursoApiController {

	private static final String CANT_MESES_PROX_CURSO_KEY = "CANT_MESES_PROX_CURSO";

	@Autowired
	@Qualifier("cursoRepository")
	private CursoRepository cursoRepository;

	@Autowired
	@Qualifier("parametroRepository")
	private ParametroRepository parametroRepository;

	@Autowired
	@Qualifier("cursoDao")
	private CursoDao cursoDao;

	@RequestMapping(method = RequestMethod.GET, value = "listar")
	public List<Curso> listar() {
		List<Curso> cursos = (List<Curso>) cursoRepository.findByEstadoCurso(Estado.PUBLICADO);
		return cursos;
	}

	@RequestMapping(method = RequestMethod.GET, value = "listar/{categoriaId}")
	public List<Curso> listarPorCategoria(@PathVariable Long categoriaId) {
		List<Curso> cursos = (List<Curso>) cursoRepository.findByCategoriaIdAndEstadoCurso(categoriaId, Estado.PUBLICADO);
		return cursos;
	}

	@RequestMapping(method = RequestMethod.GET, value = "ultimos-cursos")
	public List<Curso> ultimosCursos() {
		Parametro catidadMeses = parametroRepository.findOne(CANT_MESES_PROX_CURSO_KEY);
		Integer meses = Integer.valueOf(catidadMeses.getValor());
		Calendar hasta = Calendar.getInstance();
		hasta.add(Calendar.MONTH, meses);

		List<Curso> cursos = (List<Curso>) cursoDao.obtenerProximosCursos(Calendar.getInstance().getTime(),
				hasta.getTime());
		return cursos;
	}

}
