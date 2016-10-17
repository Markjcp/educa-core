package org.educa.core.dao;

import org.educa.core.entities.model.ComponenteId;
import org.educa.core.entities.model.Unidad;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadRepository extends CrudRepository<Unidad, ComponenteId> {

	@Query(" select u from Unidad u where u.id.numero = :numero and u.id.idCurso = :idCurso  ")
	Unidad findByNumeroAndIdCurso(@Param("numero") Integer numero, @Param("idCurso") Long idCurso);
}
