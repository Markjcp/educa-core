package org.educa.core.dao;

import java.util.List;

import org.educa.core.entities.Persistible;


public interface GeneralDao<T extends Persistible> {
	void save(T entity);

	void persist(T entity);

	void remove(T entity);

	T findById(long id);

	List<T> findAll();
}
