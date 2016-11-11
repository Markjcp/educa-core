package org.educa.core.services;

import java.util.Date;
import java.util.Map;

import org.educa.core.bean.ContadorEstadistica;
import org.educa.core.entities.model.Categoria;

public interface EstadisticaService {
	
	Map<Categoria,ContadorEstadistica> buscarEstadisticas(Date desde, Date hasta, Long usuarioId);
	
	String adaptarResultados(Map<Categoria,ContadorEstadistica> resultados);

}
