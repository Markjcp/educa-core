package org.educa.core.dao;

import java.util.List;

import org.educa.core.entities.model.Comentario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepository extends CrudRepository<Comentario, Long> {
	
	public List<Comentario> findByIdTema(Long idTema);

}
