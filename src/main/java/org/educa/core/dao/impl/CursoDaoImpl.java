package org.educa.core.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.educa.core.dao.CursoDao;
import org.educa.core.entities.model.Curso;
import org.springframework.stereotype.Repository;

@Repository(value = "cursoDao")
public class CursoDaoImpl extends GeneralDaoSupport<Curso>implements CursoDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Curso> obtenerProximosCursos(Date desde, Date hasta) {
		EntityManager em = getEntityManager();
		return (List<Curso>) em
				.createQuery(
						"from Curso c where c.fechaEstimadaProximaSesion>= :desde and c.fechaEstimadaProximaSesion<= :hasta")
				.setParameter("desde", desde).setParameter("hasta", hasta).getResultList();

	}

}
