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

	@Query(" select u from Sesion u join u.id d where u.fechaHasta >= :fechaHasta and d.idCurso = :idCurso  ")
	SortedSet<Sesion> findByFechaAndIdCurso(@Param("fechaHasta") Date fechaHasta, @Param("idCurso") Long idCurso);
}
