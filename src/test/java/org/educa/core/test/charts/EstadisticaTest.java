package org.educa.core.test.charts;

import org.junit.Test;

import com.googlecode.charts4j.BarChart;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.Data;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.Plot;
import com.googlecode.charts4j.Plots;
import com.googlecode.charts4j.Shape;

public class EstadisticaTest {
	
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
