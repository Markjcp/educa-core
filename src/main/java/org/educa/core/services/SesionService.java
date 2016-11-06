package org.educa.core.services;

import java.util.Date;
import java.util.SortedSet;

import org.educa.core.entities.model.Sesion;

public interface SesionService {
	
	SortedSet<Sesion> findActiveSesions(Date date, Long idCurso);

}
