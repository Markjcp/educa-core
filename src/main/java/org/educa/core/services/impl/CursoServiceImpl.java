package org.educa.core.services.impl;

import java.util.List;

import org.educa.core.dao.CursoDao;
import org.educa.core.entities.constants.ConstantesDelModelo;
import org.educa.core.entities.model.Curso;
import org.educa.core.exceptions.ServiceException;
import org.educa.core.services.CursoService;
import org.educa.core.services.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = ServiceException.class)
public class CursoServiceImpl implements CursoService {

	@Autowired
	CursoDao cursoDao;
	@Autowired
	NotificacionService notificacionService;

	@Override
	public void crearCurso(Curso curso) {
		// Se guarda el curso en la base de datos y se le envia notificacion al
		// profesor asignado al curso.
		curso.setLinkImagen(ConstantesDelModelo.PREFIJO_IMAGEN + "/" + curso.getId()
				+ ConstantesDelModelo.SEPARADOR_DE_IMAGEN + curso.getFoto().getOriginalFilename());
		this.cursoDao.save(curso);
		this.notificacionService.notificarCursoNuevo(curso.getDocente(), curso.getId() + "", curso.getNombre());
	}

	@Override
	public Curso encontrarCursoPorId(long id) {
		return this.cursoDao.findById(id);
	}

	@Override
	public List<Curso> obtenerTodos() {
		return this.cursoDao.findAll();
	}

	@Override
	public void eliminarCurso(long id) {
		Curso curso = this.cursoDao.findById(id);
		if (curso != null) {
			this.cursoDao.remove(curso);
		}
	}

	@Override
	public List<Curso> obtenerCursosDocente(long legajo) {
		return this.cursoDao.findByLegajo(legajo);
	}

	@Override
	public List<Curso> obtenerCursosDocente(long legajo, String nombreCurso) {
		return this.cursoDao.findByLegajoAndNombreCurso(legajo, nombreCurso);
	}

	@Override
	public void guardarCurso(Curso curso) {
		this.cursoDao.save(curso);
	}	
}
