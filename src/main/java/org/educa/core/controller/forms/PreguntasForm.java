package org.educa.core.controller.forms;

public class PreguntasForm {

	private boolean opcionUnoSeleccionada;
	private boolean opcionDosSeleccionada;
	private boolean opcionTresSeleccionada;
	private boolean opcionCuatroSeleccionada;
	private String respuestaOpcionUno;
	private String respuestaOpcionDos;
	private String respuestaOpcionTres;
	private String respuestaOpcionCuatro;
	private boolean multipleChoice;
	private String respuestaUnica;
	private String pregunta;
	private Long idCurso;
	private Integer numero;
	private Integer idPregunta;

	public boolean isOpcionUnoSeleccionada() {
		return opcionUnoSeleccionada;
	}

	public void setOpcionUnoSeleccionada(boolean opcionUnoSeleccionada) {
		this.opcionUnoSeleccionada = opcionUnoSeleccionada;
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

	public String getRespuestaOpcionUno() {
		return respuestaOpcionUno;
	}

	public void setRespuestaOpcionUno(String respuestaOpcionUno) {
		this.respuestaOpcionUno = respuestaOpcionUno;
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

	public boolean isMultipleChoice() {
		return multipleChoice;
	}

	public void setMultipleChoice(boolean multipleChoice) {
		this.multipleChoice = multipleChoice;
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

	public Integer getIdPregunta() {
		return idPregunta;
	}

	public void setIdPregunta(Integer idPregunta) {
		this.idPregunta = idPregunta;
	}

	public Long getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Long idCurso) {
		this.idCurso = idCurso;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

}
