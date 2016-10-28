package org.educa.core.dao;

import java.util.List;

import org.educa.core.entities.model.Tema;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemaRepository  extends CrudRepository<Tema, Long>{
	
	public List<Tema> findByIdForo(Long idForo);

}
