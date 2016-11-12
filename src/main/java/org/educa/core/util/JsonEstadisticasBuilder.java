package org.educa.core.util;

import org.educa.core.bean.ContadorEstadistica;
import org.educa.core.bean.EstadisticaGraficoRoleBean;
import org.educa.core.entities.model.Categoria;

public class JsonEstadisticasBuilder {
	
	private Object[] root = null;
	
	private int cantidadAgregados = 1;
	
	
	public JsonEstadisticasBuilder(final int size){
		root = new Object[size+1];		
	}
	
	public JsonEstadisticasBuilder agregarCabecera(){
		EstadisticaGraficoRoleBean role = new EstadisticaGraficoRoleBean();
		role.setRole("annotation");
		Object[] cabecera = new Object[5];
		cabecera[0]="Inscriptos";
		cabecera[1]="Aprobados";
		cabecera[2]="Desaprobados";
		cabecera[3]="No terminaron";
		cabecera[4]=role;
		root[0] = cabecera;
		return this;		
	}
	
	public JsonEstadisticasBuilder agregarRegistro(Categoria categoria, ContadorEstadistica contador){
		Object[] categoria1 = new Object[5];
		categoria1[0] = categoria.getDescripcion();
		categoria1[1] = contador.getAprobados();
		categoria1[2] = contador.getDesaprobados();
		categoria1[3] = contador.getInconcluso();
		categoria1[4] = "";
		root[cantidadAgregados] = categoria1;
		cantidadAgregados++;
		return this;
	}
	
	public Object[] construir(){	
		return root;
	}

}
