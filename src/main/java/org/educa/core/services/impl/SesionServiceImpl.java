package org.educa.core.services.impl;

import java.util.Date;
import java.util.SortedSet;

import org.educa.core.dao.SesionDao;
import org.educa.core.entities.model.Sesion;
import org.educa.core.services.SesionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SesionServiceImpl implements SesionService{
	
	@Autowired
	private SesionDao sesionDao;

	@Override
	public SortedSet<Sesion> findActiveSesions(Date date, Long idCurso) {
		return sesionDao.findByFechaAndIdCurso(date, idCurso);
	}

}
