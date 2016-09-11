package org.educa.core.dao;

import java.util.List;

import org.educa.core.entities.model.Curso;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends CrudRepository<Curso, Long>{
	
	List<Curso> findByCategoriaId(Long categoriaId);
	
	List<Curso> findByNombre(String nombre);

}
