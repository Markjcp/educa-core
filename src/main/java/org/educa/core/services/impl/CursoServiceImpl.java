package org.educa.core.services.impl;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.educa.core.dao.CursoDao;
import org.educa.core.dao.SesionRepository;
import org.educa.core.dao.UnidadRepository;
import org.educa.core.entities.constants.ConstantesDelModelo;
import org.educa.core.entities.model.ComponenteId;
import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.Sesion;
import org.educa.core.entities.model.Unidad;
import org.educa.core.exceptions.ServiceException;
import org.educa.core.services.CursoService;
import org.educa.core.services.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = ServiceException.class)
public class CursoServiceImpl implements CursoService {

	@Autowired
	private CursoDao cursoDao;
	
	@Autowired
	private NotificacionService notificacionService;
	
	@Autowired
	@Qualifier("unidadRepository")
	private UnidadRepository unidadRepository;
		
	@Autowired
	@Qualifier("sesionRepository")
	private SesionRepository sesionRepository;

	@Override
	public void crearCurso(Curso curso) {
		// Se guarda el curso en la base de datos y se le envia notificacion al
		// profesor asignado al curso.
		curso.setLinkImagen(ConstantesDelModelo.PREFIJO_IMAGEN + "/" + curso.getId()
				+ ConstantesDelModelo.SEPARADOR_DE_IMAGEN + curso.getFoto().getOriginalFilename());
		this.cursoDao.save(curso);
		this.notificacionService.notificarCursoNuevo(curso.getDocente(), curso.getId() + "", curso.getNombre());
	}

	@Override
	public Curso encontrarCursoPorId(long id) {
		return this.cursoDao.findById(id);
	}

	@Override
	public List<Curso> obtenerTodos() {
		return this.cursoDao.findAll();
	}

	@Override
	public void eliminarCurso(long id) {
		Curso curso = this.cursoDao.findById(id);
		if (curso != null) {
			this.cursoDao.remove(curso);
		}
	}

	@Override
	public List<Curso> obtenerCursosDocente(long legajo) {
		return this.cursoDao.findByLegajo(legajo);
	}

	@Override
	public List<Curso> obtenerCursosDocente(long legajo, String nombreCurso) {
		return this.cursoDao.findByLegajoAndNombreCurso(legajo, nombreCurso);
	}

	@Override
	public void guardarCurso(Curso curso) {
		this.cursoDao.save(curso);
	}

	@Override
	public void crearUnidad(Curso curso, Unidad unidadNueva) {
		if(curso != null){
			ComponenteId unidadId = new ComponenteId();
			unidadId.setIdCurso(curso.getId());
			
			//Seria la primer unidad dada de alta para el curso si unidades esta en null o vacio
			int nroNuevaUnidad = 1;
			
			//Para guardar una unidad le asigno un numero, el cual no puede estar repetido y debe de seguir en orden.
			if(curso.getUnidades() != null && !curso.getUnidades().isEmpty()){
				for(Unidad unidad : curso.getUnidades()){
					if(unidad.getId().getNumero() > nroNuevaUnidad){
						nroNuevaUnidad = unidad.getId().getNumero();
					}
				}
				
				//Incremento porque guarda el nro de unidad mas grande dentro del curso
				nroNuevaUnidad++;
			}
			
			curso.addUnidad(unidadNueva);
			unidadId.setNumero(nroNuevaUnidad);
			unidadNueva.setId(unidadId);		
			unidadNueva.setCurso(curso);
			
			this.unidadRepository.save(unidadNueva);
		}		
	}

	@Override
	public boolean eliminarUnidadCurso(Curso curso, long idUnidad, int numeroUnidad) {
		// TODO [ediaz] VER XQ ESTO NO ANDA BIEN - BUG CUANDO HAY MAS DE UNA UNIDAD CARGADA EN EL CURSO Y SE QUIERE ELIMINAR UNA
		if(curso != null && curso.getUnidades() != null){			
			SortedSet<Unidad> unidades = new TreeSet<Unidad>();
			boolean encontrado = false;
			Unidad unidadEliminar = null;
			
			//Determino cual es la unidad a eliminar
			for(Unidad unidad : curso.getUnidades()){
				if(unidad.getId().getIdCurso() == idUnidad && unidad.getId().getNumero() == numeroUnidad){
					encontrado = true;
					unidadEliminar = unidad;
				} else {
					unidades.add(unidad);
				}
			}
			
			if(encontrado){
				//PRUEBA
				curso.setUnidades(unidades);
				this.unidadRepository.delete(unidadEliminar);
				// FIN DE PRUEBA
				
				
//				int nroUnidad = unidadEliminar.getId().getNumero();		
//				this.unidadRepository.delete(unidadEliminar);
//				
//				curso = this.encontrarCursoPorId(curso.getId());
//				
//				//TODO Elimino las unidades porque sino no me deja actualizarle el nro de unidad (OjO con esto, no esta bien; hay que arreglarlo para los proximos sprint [ediaz])
//				/*for(Unidad unidad : unidades){
//					this.unidadRepository.delete(unidad);
//				}
//				si tengo q dejar esto => debo de crear primero las unidades para asignarselas al curso!
//				*/
//				
//				//Actualizo el numero de unidad
//				SortedSet<Unidad> unidadesNroOk = new TreeSet<Unidad>();
//				for(Unidad unidad : unidades){
//					if(unidad.getId().getNumero() > nroUnidad){
//						unidad.getId().setNumero(nroUnidad);
//						nroUnidad++;
//					}
//					
//					Unidad unidadPersistida = this.unidadRepository.save(unidad);
//					unidadesNroOk.add(unidadPersistida);
//				}
//				
//				curso.setUnidades(unidadesNroOk);
//				this.guardarCurso(curso);
				
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void crearSesion(Curso curso, Sesion sesionNueva) {
		if(curso != null){
			ComponenteId sesionId = new ComponenteId();
			sesionId.setIdCurso(curso.getId());
			
			//Seria la primer sesion dada de alta para el curso si sesiones esta en null o vacio
			int nroNuevaSesion = 1;
			
			//Para guardar una sesion le asigno un numero, el cual no puede estar repetido y debe de seguir en orden.
			if(curso.getSesiones() != null && !curso.getSesiones().isEmpty()){
				for(Sesion sesion : curso.getSesiones()){
					if(sesion.getId().getNumero() > nroNuevaSesion){
						nroNuevaSesion = sesion.getId().getNumero();
					}
				}
				
				//Incremento porque guarda el nro de sesion mas grande dentro del curso
				nroNuevaSesion++;
			}
			
			curso.addSesion(sesionNueva);
			sesionId.setNumero(nroNuevaSesion);
			sesionNueva.setId(sesionId);		
			sesionNueva.setCurso(curso);
			
			this.sesionRepository.save(sesionNueva);
		}
	}

	@Override
	public boolean eliminarSesionCurso(Curso curso, long idSesion, int numeroSesion) {
		// TODO [ediaz] VER XQ ESTO NO ANDA BIEN - BUG CUANDO HAY MAS DE UNA UNIDAD CARGADA EN EL CURSO Y SE QUIERE ELIMINAR UNA
		if(curso != null && curso.getSesiones() != null){			
			SortedSet<Sesion> sesiones = new TreeSet<Sesion>();
			boolean encontrado = false;
			Sesion sesionEliminar = null;
			
			//Determino cual es la sesion a eliminar
			for(Sesion sesion : curso.getSesiones()){
				if(sesion.getId().getIdCurso() == idSesion && sesion.getId().getNumero() == numeroSesion){
					encontrado = true;
					sesionEliminar = sesion;
				} else {
					sesiones.add(sesion);
				}
			}
			
			if(encontrado){
				//PRUEBA
				curso.setSesiones(sesiones);
				this.sesionRepository.delete(sesionEliminar);
				// FIN DE PRUEBA
				
				
//						int nroUnidad = unidadEliminar.getId().getNumero();		
//						this.unidadRepository.delete(unidadEliminar);
//						
//						curso = this.encontrarCursoPorId(curso.getId());
//						
//						//TODO Elimino las unidades porque sino no me deja actualizarle el nro de unidad (OjO con esto, no esta bien; hay que arreglarlo para los proximos sprint [ediaz])
//						/*for(Unidad unidad : unidades){
//							this.unidadRepository.delete(unidad);
//						}
//						si tengo q dejar esto => debo de crear primero las unidades para asignarselas al curso!
//						*/
//						
//						//Actualizo el numero de unidad
//						SortedSet<Unidad> unidadesNroOk = new TreeSet<Unidad>();
//						for(Unidad unidad : unidades){
//							if(unidad.getId().getNumero() > nroUnidad){
//								unidad.getId().setNumero(nroUnidad);
//								nroUnidad++;
//							}
//							
//							Unidad unidadPersistida = this.unidadRepository.save(unidad);
//							unidadesNroOk.add(unidadPersistida);
//						}
//						
//						curso.setUnidades(unidadesNroOk);
//						this.guardarCurso(curso);
				
				return true;
			}
		}
		
		return false;
	}
}
