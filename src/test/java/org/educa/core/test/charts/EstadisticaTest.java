package org.educa.core.test.charts;

import org.educa.core.bean.EstadisticaGraficoRoleBean;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.charts4j.BarChart;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.Data;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.Plot;
import com.googlecode.charts4j.Plots;
import com.googlecode.charts4j.Shape;

public class EstadisticaTest {
	
	@Test
	public void probarSerializar() throws JsonProcessingException{
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
		categoria1[0] = "Programacion";
		categoria1[1] = 10;
		categoria1[2] = 2;
		categoria1[3] = 3;
		categoria1[4] = "";
		
		Object[] categoria2 = new Object[5];
		categoria2[0] = "Moda";
		categoria2[1] = 11;
		categoria2[2] = 4;
		categoria2[3] = 4;
		categoria2[4] = "";
		
		Object[] categoria3 = new Object[5];
		categoria3[0] = "Gastronomía";
		categoria3[1] = 13;
		categoria3[2] = 4;
		categoria3[3] = 4;
		categoria3[4] = "";
		
		root[0] = cabecera;
		root[1] = categoria1;
		root[2] = categoria2;
		root[3] = categoria3;
		
		ObjectMapper om = new ObjectMapper();
		System.out.println(om.writeValueAsString(root));
	}
	
	@Test
	public void probarGraficoBarras(){
		Plot plot = Plots.newPlot(Data.newData(0, 10, 20, 30, 40, 50, 60, 70, 80, 90));
		//plot.
        plot.addShapeMarkers(Shape.DIAMOND, Color.BLUE, 12);
		
		BarChart barChart = GCharts.newBarChart(plot);
		barChart.setTitle("Titulo", Color.BLACK, 15);
		barChart.setSize(400, 600);
		barChart.setDataStacked(true);
		System.out.println(barChart.toURLString());
		
	}

}
