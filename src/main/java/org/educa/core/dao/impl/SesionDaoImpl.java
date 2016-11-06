package org.educa.core.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.educa.core.dao.SesionDao;
import org.educa.core.entities.model.Sesion;
import org.springframework.stereotype.Repository;

@Repository(value = "sesionDao")
public class SesionDaoImpl implements SesionDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	@SuppressWarnings("unchecked")
	public SortedSet<Sesion> findByFechaAndIdCurso(Date date, Long idCurso) {
		List<Sesion> resultado = null;
		try {
			resultado = em
					.createQuery(
							"from Sesion s where s.id.idCurso = :idCurso and s.fechaDesde <= :date and s.fechaHasta >= :date")
					.setParameter("idCurso", idCurso).setParameter("date", date).getResultList();
		} catch (NoResultException e) {
			resultado = new ArrayList<Sesion>();
		}
		return new TreeSet<Sesion>(resultado);
	}

}
