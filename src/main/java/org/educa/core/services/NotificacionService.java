package org.educa.core.services;

import org.educa.core.entities.model.Docente;

public interface NotificacionService {

	void notificarCursoNuevo(Docente profesor, String codigoCurso, String cursoNombre);
}
