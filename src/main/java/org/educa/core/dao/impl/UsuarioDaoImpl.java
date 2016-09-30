package org.educa.core.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.educa.core.dao.UsuarioDao;
import org.educa.core.entities.model.Curso;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value = "usuarioDao")
public class UsuarioDaoImpl implements UsuarioDao {

	@PersistenceContext
	EntityManager em;

	@Override
	@Transactional
	public void persistirUsuarioFacebookSinValidaciones(String idFacebook, Long rolId) {
		em.createNativeQuery("insert into usuario (id_facebook,id_rol_usuario) values ( :idFacebook , :idRol)")
				.setParameter("idFacebook", idFacebook).setParameter("idRol", rolId).executeUpdate();

	}

	@Override
	@Transactional
	public void persistirUsuarioGoogleSinValidaciones(String idGoogle, Long rolId) {
		em.createNativeQuery("insert into usuario (id_google,id_rol_usuario) values ( :idGoogle , :idRol)")
				.setParameter("idGoogle", idGoogle).setParameter("idRol", rolId).executeUpdate();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Curso> obtenerMisCursos(Long usuarioId) {
		return em.createQuery("select su.curso from SesionUsuario su where su.id.id = :usuarioId")
				.setParameter("usuarioId", usuarioId).getResultList();

	}

}
