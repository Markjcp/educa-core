package org.educa.core.dao.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.educa.core.dao.CursoDao;
import org.educa.core.dao.ForoRepository;
import org.educa.core.entities.model.Categoria;
import org.educa.core.entities.model.ComponenteId;
import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.Docente;
import org.educa.core.entities.model.Estado;
import org.educa.core.entities.model.ExamenUnidad;
import org.educa.core.entities.model.Foro;
import org.educa.core.entities.model.MaterialUnidad;
import org.educa.core.entities.model.Sesion;
import org.educa.core.entities.model.Unidad;
import org.educa.core.entities.model.VideoUnidad;
import org.educa.core.util.FechaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository(value = "cursoDao")
public class CursoDaoImpl extends GeneralDaoSupport<Curso>implements CursoDao {
	
	@Autowired
	@Qualifier("foroRepository")
	private ForoRepository foroRepository;

	@SuppressWarnings("unchecked")
	@Override
	public List<Curso> obtenerProximosCursos(Date desde, Date hasta) {
		EntityManager em = getEntityManager();
		return (List<Curso>) em
				.createQuery(
						"from Curso c where c.fechaEstimadaProximaSesion>= :desde and c.fechaEstimadaProximaSesion<= :hasta and c.estadoCurso = :estado")
				.setParameter("desde", desde).setParameter("hasta", hasta).setParameter("estado", Estado.PUBLICADO).getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Curso> findByLegajo(long legajo) {
		EntityManager em = getEntityManager();
		return (List<Curso>) em
				.createQuery(
						"SELECT c FROM Curso c JOIN c.docente d WHERE d.id = :legajo")
				.setParameter("legajo", legajo).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Curso> findByLegajoAndNombreCurso(long legajo, String nombreCurso) {
		EntityManager em = getEntityManager();
		String nombre= "%" + nombreCurso + "%";
		return (List<Curso>) em
				.createQuery(
						"SELECT c FROM Curso c JOIN c.docente d "
						+ "WHERE d.id = :legajo and c.nombre LIKE :nombreCurso")
				.setParameter("legajo", legajo).setParameter("nombreCurso", nombre).getResultList();
	}

	@Override
	public void deleteUnidad(Unidad unidad) {
		if(unidad!= null && unidad.getId()!=null && unidad.getMaterial()!= null && !unidad.getMaterial().isEmpty()){
			String queryDeleteMaterial = crearQueryDeleteUnidad("material_unidad", unidad.getId().getIdCurso(), unidad.getId().getNumero());
			Query q = this.getEntityManager().createNativeQuery(queryDeleteMaterial);			
			q.executeUpdate();
		}
		
		if(unidad!= null && unidad.getId()!=null && unidad.getExamenes()!= null && !unidad.getExamenes().isEmpty()){
			String queryDeleteOpciones = crearQueryDeleteUnidad("opcion_examen_unidad", unidad.getId().getIdCurso(), unidad.getId().getNumero());
			Query q1 = this.getEntityManager().createNativeQuery(queryDeleteOpciones);			
			q1.executeUpdate();
			
			String queryDeletePreguntas = crearQueryDeleteUnidad("pregunta_examen_unidad", unidad.getId().getIdCurso(), unidad.getId().getNumero());
			Query qPreguntas = this.getEntityManager().createNativeQuery(queryDeletePreguntas);			
			qPreguntas.executeUpdate();			
			
			String queryDeleteExamen = crearQueryDeleteUnidad("examen_unidad", unidad.getId().getIdCurso(), unidad.getId().getNumero());
			Query q = this.getEntityManager().createNativeQuery(queryDeleteExamen);			
			q.executeUpdate();
		}
		
		if(unidad!= null && unidad.getId()!=null && unidad.getVideos()!= null && !unidad.getVideos().isEmpty()){
			String queryDeleteVideos = crearQueryDeleteUnidad("video_unidad", unidad.getId().getIdCurso(), unidad.getId().getNumero());
			Query q = this.getEntityManager().createNativeQuery(queryDeleteVideos);			
			q.executeUpdate();
		}
		
		if(unidad != null && unidad.getId() != null){
			String queryDelete = crearQueryDeleteUnidad("unidad", unidad.getId().getIdCurso(), unidad.getId().getNumero());
			Query q = this.getEntityManager().createNativeQuery(queryDelete);			
			q.executeUpdate();						
		}		
	}
	
	private String crearQueryDeleteUnidad(String tabla, Long idCurso, Integer numeroComponente){
		StringBuilder query = new StringBuilder();
		query.append(" delete from " + tabla);
		query.append(" where id_curso = " + idCurso);
		query.append(" and numero_componente = " + numeroComponente);
		return query.toString();
	}

	@Override
	public void updateNumeroUnidad(Unidad unidad, int nuevoNumero) {
		if(unidad != null && unidad.getId() != null){
			StringBuilder query = new StringBuilder();
			query.append(" update unidad ");
			query.append(" set numero_componente = " + nuevoNumero);
			query.append(" where id_curso = " + unidad.getId().getIdCurso());
			query.append(" and numero_componente = " + unidad.getId().getNumero());
			
			System.out.println("QUERY - updateNumeroUnidad: " + query.toString());
			
			Query q = this.getEntityManager().createNativeQuery(query.toString());
			
			q.executeUpdate();						
		}
	}

	@Override
	public Curso findCursoByIdHidratado(long id) throws Exception {
		// Datos basicos del curso
		//Curso curso = this.findById(id);		
		
		// Datos basicos del curso
		StringBuilder query = new StringBuilder();
		query.append(" select c.id_curso , c.nombre , c.descripcion , c.legajo_docente ,  d.id_usuario , c.id_categoria , cat.descripcion as descripcionCategoria , c.imagen ,  ");
		query.append(" c.link_imagen , c.valoracion_promedio , c.cantidad_valoraciones , c.fecha_estimada_prox_sesion , c.estado_curso ");
		query.append(" from curso c  ");
		query.append(" inner join docente d  on (c.legajo_docente = d.legajo) ");
		query.append(" inner join categoria cat  on (c.id_categoria = cat.id_categoria) ");
		query.append(" where c.id_curso = " + id);
		
		Curso curso = null;
		Query qCurso = this.getEntityManager().createNativeQuery(query.toString());
		try{
			Object[] fila = (Object[]) qCurso.getSingleResult();

			if(fila != null && fila.length != 0){
				curso = new Curso();
				curso.setId(((Integer) fila[0]).longValue());
				curso.setNombre((String) fila[1]);
				curso.setDescripcion((String) fila[2]);
				
				//TODO [ediaz] - Esto se debe de mejorar, deberia de cargar el docente completo.
				Docente docente = new Docente();
				docente.setId(((BigInteger) fila[3]).longValue());
				docente.setIdUsuario(((BigInteger) fila[4]).longValue());
				curso.setDocente(docente);
				
				Categoria categoria = new Categoria();
				categoria.setId(((Integer) fila[5]).longValue());
				categoria.setDescripcion((String) fila[6]);
				curso.setCategoria(categoria);
				curso.setCategoriaId(categoria.getId());
				
				curso.setImagen((byte[]) fila[7]);
				curso.setLinkImagen((String) fila[8]);
				curso.setValoracionesPromedio(((Integer) fila[9]).intValue());
				String estadoCursoBase = (String) fila[12];
				
				if(Estado.PUBLICADO.name().equals(estadoCursoBase)){
					curso.setEstadoCurso(Estado.PUBLICADO);
				} else {
					curso.setEstadoCurso(Estado.NO_PUBLICADO);
				}
				
				//curso.setFechaEstimadaProximaSesion();						
			}
			
			
		}  catch(NoResultException e){
			
		} catch (Exception e) {
			throw e;
		}
				
		//Le actualizo las unidades		
		query = new StringBuilder();
		query.append(" select numero_componente, id_curso, titulo, descripcion, duracion_estimada  ");		
		query.append(" from unidad   ");
		query.append(" where id_curso = " + id);
		
		Query q = this.getEntityManager().createNativeQuery(query.toString());
		try{
			List<Object[]> filasUnidad = q.getResultList();

			if(filasUnidad != null){
				SortedSet<Unidad> unidades = new TreeSet<Unidad>();
				for(Object[] fila : filasUnidad){
					Unidad unidad = new Unidad();
					ComponenteId idComponente = new ComponenteId();
					idComponente.setNumero(((Integer) fila[0]).intValue());
					idComponente.setIdCurso(((Integer) fila[1]).longValue());
					
					unidad.setId(idComponente);
					
					unidad.setTitulo((String) fila[2]);
					unidad.setDescripcion((String) fila[3]);
					unidad.setDuracionEstimada(((Integer) fila[4]).intValue());
					
					unidades.add(unidad);
				}
				
				curso.setUnidades(unidades);
			}
			
		}  catch(NoResultException e){
			
		} catch (Exception e) {
			throw e;
		}
		
		//Le actualizo las sesiones		
		query = new StringBuilder();
		query.append(" select numero_componente , id_curso, fecha_desde, fecha_hasta, cupos, costo, fecha_desde_inscripcion, fecha_hasta_inscripcion , id_foro ");		
		query.append(" from sesion   ");
		query.append(" where id_curso = " + id);
		
		Query qSesion = this.getEntityManager().createNativeQuery(query.toString());
		try{
			List<Object[]> filasSesiones = qSesion.getResultList();

			if(filasSesiones != null){
				SortedSet<Sesion> sesiones = new TreeSet<Sesion>();
				for(Object[] fila : filasSesiones){
					Sesion sesion = new Sesion();
					ComponenteId idComponente = new ComponenteId();
					idComponente.setNumero(((Integer) fila[0]).intValue());
					idComponente.setIdCurso(((Integer) fila[1]).longValue());
					
					sesion.setId(idComponente);
					sesion.setFechaDesde(FechaUtil.formateFechaDDMMYYYYUsa((Date) fila[2]));
					sesion.setFechaHasta(FechaUtil.formateFechaDDMMYYYYUsa((Date) fila[3]));
					/* Estos hay q llenarlos cuando los usemos 
					 sesion.setCupos(((BigInteger) fila[4]).intValue());
					sesion.setCosto(new BigDecimal());
					*/
					
					sesion.setFechaDesdeInscripcion(FechaUtil.formateFechaDDMMYYYYUsa((Date) fila[6]));
					sesion.setFechaHastaInscripcion(FechaUtil.formateFechaDDMMYYYYUsa((Date) fila[7]));
					
					long idForo = ((BigInteger) fila[8]).longValue();					
					Foro foro = foroRepository.findOne(idForo);
					sesion.setForo(foro);
					
					sesiones.add(sesion);
				}
				
				curso.setSesiones(sesiones);
			}
			
		}  catch(NoResultException e){
			
		} catch (Exception e) {
			throw e;
		}
		
		return curso;
	}
	
	@Override
	public void deleteSesion(Sesion sesion) {
		if(sesion != null && sesion.getId() != null){
			StringBuilder query = new StringBuilder();
			query.append(" delete from sesion ");
			query.append(" where id_curso = " + sesion.getId().getIdCurso());
			query.append(" and numero_componente = " + sesion.getId().getNumero());
			
			System.out.println("QUERY - deleteSesion: " + query.toString());
			
			Query q = this.getEntityManager().createNativeQuery(query.toString());
			
			q.executeUpdate();			
		}
	}
	
	@Override
	public void updateNumeroSesion(Sesion sesion, int nuevoNumero) {
		if(sesion != null && sesion.getId() != null){
			StringBuilder query = new StringBuilder();
			query.append(" update sesion ");
			query.append(" set numero_componente = " + nuevoNumero);
			query.append(" where id_curso = " + sesion.getId().getIdCurso());
			query.append(" and numero_componente = " + sesion.getId().getNumero());
			
			System.out.println("QUERY - updateNumeroSesion: " + query.toString());
			
			Query q = this.getEntityManager().createNativeQuery(query.toString());
			
			q.executeUpdate();						
		}
	}
	
	@Override
	public void deleteCurso(Curso curso) {
		if(curso != null && curso.getId() != null){
			StringBuilder query = new StringBuilder();
			query.append(" delete from curso ");
			query.append(" where id_curso = " + curso.getId());
			
			System.out.println("QUERY - deleteCurso: " + query.toString());
			
			Query q = this.getEntityManager().createNativeQuery(query.toString());
			
			q.executeUpdate();			
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExamenUnidad> findExamenUnidadByNumeroAndIdCurso(Integer numero, Long idCurso) {		
		EntityManager em = getEntityManager();		
		return (List<ExamenUnidad>) em
				.createQuery(
						"SELECT u FROM ExamenUnidad u  JOIN u.id d WHERE d.numero = :numero and d.idCurso = :idCurso ")
				.setParameter("numero", numero).setParameter("idCurso", idCurso).getResultList();		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MaterialUnidad> findMaterialUnidadByNumeroAndIdCurso(Integer numero, Long idCurso) {
		EntityManager em = getEntityManager();
		return (List<MaterialUnidad>) em
				.createQuery(
						"SELECT u FROM MaterialUnidad u JOIN u.id d WHERE d.numero = :numero and d.idCurso = :idCurso ")
				.setParameter("numero", numero).setParameter("idCurso", idCurso).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VideoUnidad> findVideoUnidadByNumeroAndIdCurso(Integer numero, Long idCurso) {
		EntityManager em = getEntityManager();
		return (List<VideoUnidad>) em
				.createQuery(
						"SELECT u FROM VideoUnidad u JOIN u.id d WHERE d.numero = :numero and d.idCurso = :idCurso ")
				.setParameter("numero", numero).setParameter("idCurso", idCurso).getResultList();
	}
}
