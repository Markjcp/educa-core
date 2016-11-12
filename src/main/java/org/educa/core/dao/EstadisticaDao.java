package org.educa.core.dao;

import java.util.Date;
import java.util.List;

import org.educa.core.entities.model.SesionUsuario;

public interface EstadisticaDao {
	
	List<SesionUsuario> obtenerSesionesTerminadas(Date desde, Date hasta, Long usuarioId);

}
