package org.educa.core.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.educa.core.dao.GeneralDao;
import org.educa.core.entities.Persistible;
import org.educa.core.exceptions.EducaDataAccessException;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public abstract class GeneralDaoSupport<T extends Persistible> implements GeneralDao<T> {

	@PersistenceContext
	private EntityManager entityManager;

	protected Class<T> entityClass;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
	}

	public void persist(T entity) {
		entityManager.persist(entity);
	}

	public void remove(T entity) throws EducaDataAccessException {
		merge(entity);
		entityManager.remove(entity);
	}

	public void save(T entity) throws EducaDataAccessException {
		if (entity.getId() == null) {
			persist(entity);
		} else {
			merge(entity);
		}
	}

	private void merge(T entity) throws EducaDataAccessException {
		entityManager.merge(entity);
	}

	public T findById(long id) {
		return entityManager.find(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return (List<T>) entityManager.createQuery("from " + entityClass.getName()).getResultList();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public Class<T> getEntityClass(){
		return this.entityClass;
	}
}
