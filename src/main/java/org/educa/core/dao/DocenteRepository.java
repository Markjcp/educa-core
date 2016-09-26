package org.educa.core.dao;

import java.util.List;

import org.educa.core.entities.model.Docente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocenteRepository extends CrudRepository<Docente, Long> {
	
	public List<Docente> findByIdUsuario(Long idUsuario);

}
