package org.educa.core.bean;

import org.educa.core.entities.model.Categoria;

public class ResultadosEstadisticas {

	private Categoria categoria;

	private ContadorEstadistica contador;

	public ResultadosEstadisticas(Categoria categoria, ContadorEstadistica contador) {
		super();
		this.categoria = categoria;
		this.contador = contador;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public ContadorEstadistica getContador() {
		return contador;
	}

	public void setContador(ContadorEstadistica contador) {
		this.contador = contador;
	}

}
