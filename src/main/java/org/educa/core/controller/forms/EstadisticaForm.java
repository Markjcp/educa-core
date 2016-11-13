package org.educa.core.controller.forms;

import java.util.Date;
import java.util.List;

import org.educa.core.bean.ResultadosEstadisticas;
import org.springframework.format.annotation.DateTimeFormat;

public class EstadisticaForm {

	private String chartUrl;

	private String chartContentData;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date fechaDesde;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date fechaHasta;
	
	private boolean sinResultados = false;
	
	private List<ResultadosEstadisticas> resultados;

	public String getChartUrl() {
		return chartUrl;
	}

	public void setChartUrl(String chartUrl) {
		this.chartUrl = chartUrl;
	}

	public String getChartContentData() {
		return chartContentData;
	}

	public void setChartContentData(String chartContentData) {
		this.chartContentData = chartContentData;
		if(chartContentData== null || chartContentData.isEmpty()){
			sinResultados = true;
		}
	}
	
	public boolean isSinResultados() {
		return sinResultados;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public List<ResultadosEstadisticas> getResultados() {
		return resultados;
	}

	public void setResultados(List<ResultadosEstadisticas> resultados) {
		this.resultados = resultados;
	}
}
