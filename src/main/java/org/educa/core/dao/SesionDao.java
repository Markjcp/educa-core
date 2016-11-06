package org.educa.core.dao;

import java.util.Date;
import java.util.SortedSet;

import org.educa.core.entities.model.Sesion;

public interface SesionDao {

	SortedSet<Sesion> findByFechaAndIdCurso(Date date, Long idCurso);
}
