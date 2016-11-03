package org.educa.core.entities.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;



@Entity
@Table(name = "evaluacion")
public class Evaluacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8761181380516228186L;
	
	
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id_evaluacion")
	private Long id;
	
	@Column(name = "id_usuario")
	private Integer idUsuario;
	
	@Column(name = "id_sesion")
	private Integer idSesion;
	
	@Column(name = "id_curso")
	private Integer idCurso;
	
	@Column(name = "numero_unidad")
	private Integer numeroUnidad;
	
	
	@Column(name = "cantidad_respuestas_correctas")
	private Integer cantidadRespuestasCorrectas;
	
	@Column(name = "cantidad_preguntas_totales")
	private Integer cantidadPreguntasTotales;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "estado_examen")
	private EstadoExamen estado;
	
	@Column(name = "fecha_actualizacion")
	private Date fechaActualizacion;
	
	@Transient
	private String porcentaje;
	

	public Evaluacion() {
		super();
		this.setEstado(EstadoExamen.PENDIENTE);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdSesion() {
		return idSesion;
	}

	public void setIdSesion(Integer idSesion) {
		this.idSesion = idSesion;
	}

	public Integer getCantidadRespuestasCorrectas() {
		return cantidadRespuestasCorrectas;
	}

	public void setCantidadRespuestasCorrectas(Integer cantidadRespuestasCorrectas) {
		this.cantidadRespuestasCorrectas = cantidadRespuestasCorrectas;
	}

	public Integer getCantidadPreguntasTotales() {
		return cantidadPreguntasTotales;
	}

	public void setCantidadPreguntasTotales(Integer cantidadPreguntasTotales) {
		this.cantidadPreguntasTotales = cantidadPreguntasTotales;
	}

	public EstadoExamen getEstado() {
		return estado;
	}

	public void setEstado(EstadoExamen estado) {
		this.estado = estado;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public String getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}

	public Integer getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Integer idCurso) {
		this.idCurso = idCurso;
	}

	public Integer getNumeroUnidad() {
		return numeroUnidad;
	}

	public void setNumeroUnidad(Integer numeroUnidad) {
		this.numeroUnidad = numeroUnidad;
	}
	
	
	


}
