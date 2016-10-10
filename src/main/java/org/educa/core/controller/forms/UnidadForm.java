package org.educa.core.controller.forms;

import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.Unidad;

public class UnidadForm {

	private Curso curso;	
	private boolean publicado;
	
	private Unidad unidad;
	
	private boolean opcionUnaSeleccionada;
	private boolean opcionDosSeleccionada;
	private boolean opcionTresSeleccionada;
	private boolean opcionCuatroSeleccionada;
	
	private String respuestaOpcionUna;
	private String respuestaOpcionDos;
	private String respuestaOpcionTres;
	private String respuestaOpcionCuatro;
	
	private String esMultipleChoice;
	
	private String respuestaUnica;
	
	private String pregunta;

	public UnidadForm() {
		super();
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public boolean isPublicado() {
		return publicado;
	}

	public void setPublicado(boolean publicado) {
		this.publicado = publicado;
	}

	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}

	public boolean isOpcionUnaSeleccionada() {
		return opcionUnaSeleccionada;
	}

	public void setOpcionUnaSeleccionada(boolean opcionUnaSeleccionada) {
		this.opcionUnaSeleccionada = opcionUnaSeleccionada;
	}

	public boolean isOpcionDosSeleccionada() {
		return opcionDosSeleccionada;
	}

	public void setOpcionDosSeleccionada(boolean opcionDosSeleccionada) {
		this.opcionDosSeleccionada = opcionDosSeleccionada;
	}

	public boolean isOpcionTresSeleccionada() {
		return opcionTresSeleccionada;
	}

	public void setOpcionTresSeleccionada(boolean opcionTresSeleccionada) {
		this.opcionTresSeleccionada = opcionTresSeleccionada;
	}

	public boolean isOpcionCuatroSeleccionada() {
		return opcionCuatroSeleccionada;
	}

	public void setOpcionCuatroSeleccionada(boolean opcionCuatroSeleccionada) {
		this.opcionCuatroSeleccionada = opcionCuatroSeleccionada;
	}

	public String getRespuestaOpcionUna() {
		return respuestaOpcionUna;
	}

	public void setRespuestaOpcionUna(String respuestaOpcionUna) {
		this.respuestaOpcionUna = respuestaOpcionUna;
	}

	public String getRespuestaOpcionDos() {
		return respuestaOpcionDos;
	}

	public void setRespuestaOpcionDos(String respuestaOpcionDos) {
		this.respuestaOpcionDos = respuestaOpcionDos;
	}

	public String getRespuestaOpcionTres() {
		return respuestaOpcionTres;
	}

	public void setRespuestaOpcionTres(String respuestaOpcionTres) {
		this.respuestaOpcionTres = respuestaOpcionTres;
	}

	public String getRespuestaOpcionCuatro() {
		return respuestaOpcionCuatro;
	}

	public void setRespuestaOpcionCuatro(String respuestaOpcionCuatro) {
		this.respuestaOpcionCuatro = respuestaOpcionCuatro;
	}

	public String getEsMultipleChoice() {
		return esMultipleChoice;
	}

	public void setEsMultipleChoice(String esMultipleChoice) {
		this.esMultipleChoice = esMultipleChoice;
	}

	public String getRespuestaUnica() {
		return respuestaUnica;
	}

	public void setRespuestaUnica(String respuestaUnica) {
		this.respuestaUnica = respuestaUnica;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}	
}
