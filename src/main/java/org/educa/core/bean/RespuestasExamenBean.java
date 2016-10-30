package org.educa.core.bean;

import java.util.List;

public class RespuestasExamenBean {

	private Long idUsuario;
	private List<RespuestaExamenBean> respuestas;
	
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public List<RespuestaExamenBean> getRespuestas() {
		return respuestas;
	}
	public void setRespuestas(List<RespuestaExamenBean> respuestas) {
		this.respuestas = respuestas;
	}
	
	
}
