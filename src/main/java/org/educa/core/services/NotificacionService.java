package org.educa.core.services;

import org.educa.core.entities.model.Docente;
import org.educa.core.entities.model.Usuario;

public interface NotificacionService {

	void notificarCursoNuevo(Docente profesor, String codigoCurso, String cursoNombre);
	
	void enviarActivacion(Usuario usuario);
}
