package org.educa.core.dao;

import java.util.List;

import org.educa.core.entities.model.MaterialUnidad;
import org.educa.core.entities.model.MaterialUnidadId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialUnidadRepository extends CrudRepository<MaterialUnidad, MaterialUnidadId>{

	@Query(" select u from MaterialUnidad u where u.id.numero = :numero and u.id.idCurso = :idCurso  ")
	List<MaterialUnidad> findByNumeroAndIdCurso(@Param("numero") Integer numero, @Param("idCurso") Long idCurso);
}
