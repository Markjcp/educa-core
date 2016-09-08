package org.educa.core.services.impl;

import org.educa.core.entities.model.Docente;
import org.educa.core.exceptions.ServiceException;
import org.educa.core.services.NotificacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, noRollbackFor=ServiceException.class)
public class NotificacionServiceImpl implements NotificacionService {

	@Override
	public void notificarCursoNuevo(Docente profesor, String codigoCurso, String cursoNombre) {
		// TODO [ediaz] hay que mandar el mail al profe de que tiene asignado un nuevo curso

	}

}
