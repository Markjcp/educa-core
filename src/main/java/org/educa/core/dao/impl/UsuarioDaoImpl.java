package org.educa.core.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.educa.core.dao.UsuarioDao;
import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.SesionUsuarioId;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value = "usuarioDao")
public class UsuarioDaoImpl implements UsuarioDao {

	@PersistenceContext
	EntityManager em;

	@Override
	@Transactional
	public void persistirUsuarioFacebookSinValidaciones(String idFacebook, Long rolId, String nombre, String apellido) {
		em.createNativeQuery("insert into usuario (id_facebook,id_rol_usuario, nombre, apellido) values ( :idFacebook , :idRol, :nombre, :apellido)")
				.setParameter("idFacebook", idFacebook)
				.setParameter("idRol", rolId)
				.setParameter("nombre", nombre)
				.setParameter("apellido", apellido)
				.executeUpdate();

	}

	@Override
	@Transactional
	public void persistirUsuarioGoogleSinValidaciones(String idGoogle, Long rolId, String nombre, String apellido) {
		em.createNativeQuery("insert into usuario (id_google,id_rol_usuario, nombre, apellido) values ( :idGoogle , :idRol, :nombre, :apellido)")
				.setParameter("idGoogle", idGoogle)
				.setParameter("idRol", rolId)
				.setParameter("nombre", nombre)
				.setParameter("apellido", apellido)
				.executeUpdate();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Curso> obtenerMisCursos(Long usuarioId) {
		return em.createQuery("select su.curso from SesionUsuario su where su.id.id = :usuarioId")
				.setParameter("usuarioId", usuarioId).getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SesionUsuarioId> obtenerMisSesiones(Long usuarioId) {
		return em.createQuery("select su.id from SesionUsuario su where su.id.id = :usuarioId")
				.setParameter("usuarioId", usuarioId).getResultList();
	}

}
