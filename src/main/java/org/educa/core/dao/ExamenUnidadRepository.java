package org.educa.core.dao;

import java.util.List;

import org.educa.core.entities.model.ExamenUnidad;
import org.educa.core.entities.model.ExamenUnidadId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamenUnidadRepository extends CrudRepository<ExamenUnidad, ExamenUnidadId> {

	@Query(" select u from ExamenUnidad u where u.id.numero = :numero and u.id.idCurso = :idCurso  ")
	List<ExamenUnidad> findByNumeroAndIdCurso(@Param("numero") Integer numero, @Param("idCurso") Long idCurso);
}
