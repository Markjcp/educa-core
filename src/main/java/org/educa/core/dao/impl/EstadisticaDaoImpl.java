package org.educa.core.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.educa.core.dao.EstadisticaDao;
import org.educa.core.entities.model.SesionUsuario;
import org.springframework.stereotype.Repository;

@Repository(value = "estadisticaDao")
public class EstadisticaDaoImpl implements EstadisticaDao{
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<SesionUsuario> obtenerSesionesTerminadas(Date desde, Date hasta, Long usuarioId) {
		String baseQuery = "from SesionUsuario su where su.sesion.fechaHasta < :hoy ";
		String queryMedia = "";
		if(desde!=null){
			queryMedia += "and su.sesion.fechaHasta >= :desde ";
		}
		if(hasta!=null){
			queryMedia += "and su.sesion.fechaHasta <= :hasta ";
		}
		if(usuarioId!=null){
			queryMedia += "and su.curso.docente.usuario.id = :usuarioId ";
		}
		Query query = em.createQuery(baseQuery+queryMedia);
		query.setParameter("hoy", new Date());
		if(desde!=null){
			query.setParameter("desde", desde);
		}
		if(hasta!=null){
			query.setParameter("hasta", hasta);			
		}
		if(usuarioId!=null){
			query.setParameter("usuarioId", usuarioId);
		}		
		return query.getResultList();
	}

}
