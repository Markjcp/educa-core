package org.educa.core.services;

import org.educa.core.entities.model.Profesor;

public interface NotificacionService {

	void notificarCursoNuevo(Profesor profesor, String codigoCurso, String cursoNombre);
}
