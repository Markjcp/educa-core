package org.educa.core.dao.impl;

import org.educa.core.dao.CursoDao;
import org.educa.core.entities.model.Curso;
import org.springframework.stereotype.Repository;

@Repository(value = "cursoDao")
public class CursoDaoImpl extends GeneralDaoSupport<Curso> implements CursoDao {

}
