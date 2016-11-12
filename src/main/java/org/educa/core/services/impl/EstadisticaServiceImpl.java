package org.educa.core.services.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.educa.core.bean.ContadorEstadistica;
import org.educa.core.bean.EstadisticaGraficoRoleBean;
import org.educa.core.dao.EstadisticaDao;
import org.educa.core.entities.model.Categoria;
import org.educa.core.entities.model.EstadoSesionUsuario;
import org.educa.core.entities.model.SesionUsuario;
import org.educa.core.exceptions.SerializacionException;
import org.educa.core.exceptions.SinResultadosException;
import org.educa.core.services.EstadisticaService;
import org.educa.core.util.JsonEstadisticasBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EstadisticaServiceImpl implements EstadisticaService{
	
	@Autowired
	private EstadisticaDao estadisticaDao;

	@Override
	public Map<Categoria, ContadorEstadistica> buscarEstadisticas(Date desde, Date hasta, Long usuarioId) {
		List<SesionUsuario> sesiones = estadisticaDao.obtenerSesionesTerminadas(desde, hasta, usuarioId);
		Map<Categoria, ContadorEstadistica> resultado = adaptarSesiones(sesiones); 
		return resultado;
	}

	private Map<Categoria, ContadorEstadistica> adaptarSesiones(List<SesionUsuario> sesiones) {
		Map<Categoria, ContadorEstadistica> resultado = new HashMap<Categoria, ContadorEstadistica>();
		for (SesionUsuario sesionUsuario : sesiones) {
			Categoria categoria = sesionUsuario.getCurso().getCategoria();
			ContadorEstadistica contador = null;
			if(!resultado.containsKey(sesionUsuario.getCurso().getCategoria())){
				contador = new ContadorEstadistica();				
			}else{
				contador = resultado.get(categoria);
			}
			sumarInscripto(contador, sesionUsuario);
			resultado.put(categoria, contador);
		}
		return resultado;
	}
	
	private void sumarInscripto(ContadorEstadistica contador, SesionUsuario sesionUsuario){
		EstadoSesionUsuario estado = sesionUsuario.getEstado();
		if(estado.equals(EstadoSesionUsuario.APROBADO)){
			contador.sumarAprobados();
		}else if(estado.equals(EstadoSesionUsuario.DESAPROBADO)){
			contador.sumarDesaprobados();
		}else if(estado.equals(EstadoSesionUsuario.INDEFINIDO)){
			contador.sumarInconcluso();
		}
	}

	@Override
	public String adaptarResultados(Map<Categoria, ContadorEstadistica> resultados) {
		//return adaptarResultadosHardcodeado(resultados); Si se quiere tener una idea sin muchos datos descomentar esta línea y comentar el resto
		if(resultados.isEmpty()){
			throw new SinResultadosException();
		}
		
		JsonEstadisticasBuilder builder = new JsonEstadisticasBuilder(resultados.keySet().size());
		builder.agregarCabecera();
		for (Categoria entradaMapa : resultados.keySet()) {
			builder.agregarRegistro(entradaMapa, resultados.get(entradaMapa));
		}
		
		ObjectMapper om = new ObjectMapper();
		try {
			return om.writeValueAsString(builder.construir());
		} catch (JsonProcessingException e) {
			throw new SerializacionException();
		}		
	}
	
	public String adaptarResultadosHardcodeado(Map<Categoria, ContadorEstadistica> resultados) {
		Object[] root = new Object[4];
		
		EstadisticaGraficoRoleBean role = new EstadisticaGraficoRoleBean();
		role.setRole("annotation");
		Object[] cabecera = new Object[5];
		cabecera[0]="Inscriptos";
		cabecera[1]="Aprobados";
		cabecera[2]="Desaprobados";
		cabecera[3]="No terminaron";
		cabecera[4]=role;
		
		Object[] categoria1 = new Object[5];
		categoria1[0] = "Programacion I";
		categoria1[1] = 10;
		categoria1[2] = 2;
		categoria1[3] = 3;
		categoria1[4] = "";
		
		Object[] categoria2 = new Object[5];
		categoria2[0] = "Moda";
		categoria2[1] = 11;
		categoria2[2] = 4;
		categoria2[3] = 6;
		categoria2[4] = "";
		
		Object[] categoria3 = new Object[5];
		categoria3[0] = "Gastronomía";
		categoria3[1] = 13;
		categoria3[2] = 4;
		categoria3[3] = 5;
		categoria3[4] = "";
		
		root[0] = cabecera;
		root[1] = categoria1;
		root[2] = categoria2;
		root[3] = categoria3;
		
		ObjectMapper om = new ObjectMapper();
		try {
			return om.writeValueAsString(root);
		} catch (JsonProcessingException e) {
			throw new SerializacionException();
		}		
	}

}
