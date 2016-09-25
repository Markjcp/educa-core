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

	@SuppressWarnings("unchecked")
	@Override
	public List<Curso> findByLegajo(long legajo) {
		EntityManager em = getEntityManager();
		return (List<Curso>) em
				.createQuery(
						"SELECT c FROM Curso c JOIN c.docente d WHERE d.id = :legajo")
				.setParameter("legajo", legajo).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Curso> findByLegajoAndNombreCurso(long legajo, String nombreCurso) {
		EntityManager em = getEntityManager();
		String nombre= "%" + nombreCurso + "%";
		return (List<Curso>) em
				.createQuery(
						"SELECT c FROM Curso c JOIN c.docente d "
						+ "WHERE d.id = :legajo and c.nombre LIKE :nombreCurso")
				.setParameter("legajo", legajo).setParameter("nombreCurso", nombre).getResultList();
	}

}
