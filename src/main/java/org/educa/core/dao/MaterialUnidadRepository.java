package org.educa.core.dao;

import java.util.List;

import org.educa.core.entities.model.MaterialUnidad;
import org.educa.core.entities.model.MaterialUnidadId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialUnidadRepository extends CrudRepository<MaterialUnidad, MaterialUnidadId>{
	
	public List<MaterialUnidad> findByNumeroAndIdCurso(Integer numero, Long idCurso);

}
