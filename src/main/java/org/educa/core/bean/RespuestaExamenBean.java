package org.educa.core.bean;

public class RespuestaExamenBean {

	private Integer idPregunta;
	private Boolean multipleChoice;
	private Integer idOpcionElegida;
	private String respuesta;
	
	public Integer getIdPregunta() {
		return idPregunta;
	}
	public void setIdPregunta(Integer idPregunta) {
		this.idPregunta = idPregunta;
	}
	public Boolean isMultipleChoice() {
		return multipleChoice;
	}
	public void setMultipleChoice(Boolean multipleChoice) {
		this.multipleChoice = multipleChoice;
	}
	public Integer getIdOpcionElegida() {
		return idOpcionElegida;
	}
	public void setIdOpcionElegida(Integer idOpcionElegida) {
		this.idOpcionElegida = idOpcionElegida;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	
	
}
