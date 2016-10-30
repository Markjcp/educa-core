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
	private Long idUsuario;
	
	@Column(name = "numero_componente")
	private Integer idSesion;
	
	@Column(name = "cantidad_respuestas_correctas")
	private Integer cantidadRespuestasCorrectas;
	
	@Column(name = "cantidad_respuestas_incorrectas")
	private Integer cantidadRespuestasIncorrectas;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "estado_examen")
	private EstadoExamen estado;
	
	@Column(name = "fecha_actualizacion")
	private Date fechaActualizacion;
	
	
	

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

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
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

	public Integer getCantidadRespuestasInorrectas() {
		return cantidadRespuestasIncorrectas;
	}

	public void setCantidadRespuestasIncorrectas(Integer cantidadRespuestasIncorrectas) {
		this.cantidadRespuestasIncorrectas = cantidadRespuestasIncorrectas;
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
	
	
	

}
