package org.educa.core.dao;

import java.util.Date;
import java.util.SortedSet;

import org.educa.core.entities.model.ComponenteId;
import org.educa.core.entities.model.Sesion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SesionRepository extends CrudRepository<Sesion, ComponenteId> {

	@Query(" select u from Sesion u where u.fechaHasta = :fechaHasta and u.id.idCurso = :idCurso  ")
	SortedSet<Sesion> findByFechaAndIdCurso(@Param("fechaHasta") Date fechaHasta, @Param("idCurso") Long idCurso);
}
