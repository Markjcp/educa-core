package org.educa.core.bean;

public class ContadorEstadistica {

	private int desaprobados;

	private int aprobados;

	private int inconcluso;

	public int getInscriptos() {
		return desaprobados + aprobados + inconcluso;
	}

	public int getDesaprobados() {
		return desaprobados;
	}

	public void sumarDesaprobados() {
		this.desaprobados = desaprobados +1;
	}

	public int getAprobados() {
		return aprobados;
	}

	public void sumarAprobados() {
		this.aprobados = aprobados +1;
	}

	public int getInconcluso() {
		return inconcluso;
	}

	public void sumarInconcluso() {
		this.inconcluso = inconcluso + 1;
	}

}
