package org.educa.core.services.impl;


import org.educa.core.dao.CursoDao;
import org.educa.core.entities.model.Curso;
import org.educa.core.exceptions.ServiceException;
import org.educa.core.services.CursoService;
import org.educa.core.services.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, noRollbackFor=ServiceException.class)
public class CursoServiceImpl implements CursoService {
	
	@Autowired CursoDao cursoDao;
	@Autowired NotificacionService notificacionService;
	
	@Override
	public void crearCurso(Curso curso) {
		//Se guarda el curso en la base de datos y se le envia notificacion al profesor asignado al curso.
		this.cursoDao.save(curso);
		this.notificacionService.notificarCursoNuevo(curso.getProfesor(), curso.getCodigo(), curso.getNombre());		
	}

}
