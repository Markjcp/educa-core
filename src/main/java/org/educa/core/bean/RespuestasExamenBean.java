package org.educa.core.bean;

import java.util.List;

public class RespuestasExamenBean {

	// identifica examen
	private Integer idCurso;
	private Integer numeroUnidad;
	
	// identifica inscripcion
	private Integer idUsuario;
	private Integer idSesion;
	
	private Integer cantDePreguntas;
	private Integer cantDePregAprobadas;
	private String estado;
	
	
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Integer getIdCurso() {
		return idCurso;
	}
	public void setIdCurso(Integer idCurso) {
		this.idCurso = idCurso;
	}
	public Integer getIdSesion() {
		return idSesion;
	}
	public void setIdSesion(Integer idSesion) {
		this.idSesion = idSesion;
	}
	public Integer getCantDePreguntas() {
		return cantDePreguntas;
	}
	public void setCantDePreguntas(Integer cantDePreguntas) {
		this.cantDePreguntas = cantDePreguntas;
	}
	public Integer getCantDePregAprobadas() {
		return cantDePregAprobadas;
	}
	public void setCantDePregAprobadas(Integer cantDePregAprobadas) {
		this.cantDePregAprobadas = cantDePregAprobadas;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Integer getNumeroUnidad() {
		return numeroUnidad;
	}
	public void setNumeroUnidad(Integer numeroUnidad) {
		this.numeroUnidad = numeroUnidad;
	}
	
	
}
