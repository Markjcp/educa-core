package org.educa.core.entities.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "sesion")
public class Sesion implements Serializable{

	private static final long serialVersionUID = -219703370172178413L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id_sesion")
	private Long id;
	
	@Column(name = "fecha_desde")
	private Date fechaDesde;
	
	@Column(name = "fecha_hasta")
	private Date fechaHasta;
	
	@Column(name = "cupos")
	private Integer cupos;
	
	@Column(name = "costo")
	private BigDecimal costo;
	
	@ManyToOne
	@JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
	@JsonIgnore
	private Curso curso;
	
	@Column(name = "fecha_desde_inscripcion")
	private Date fechaDesdeInscripcion;
	
	@Column(name = "fecha_hasta_inscripcion")
	private Date fechaHastaInscripcion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getCupos() {
		return cupos;
	}

	public void setCupos(Integer cupos) {
		this.cupos = cupos;
	}

	public BigDecimal getCosto() {
		return costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Date getFechaDesdeInscripcion() {
		return fechaDesdeInscripcion;
	}

	public void setFechaDesdeInscripcion(Date fechaDesdeInscripcion) {
		this.fechaDesdeInscripcion = fechaDesdeInscripcion;
	}

	public Date getFechaHastaInscripcion() {
		return fechaHastaInscripcion;
	}

	public void setFechaHastaInscripcion(Date fechaHastaInscripcion) {
		this.fechaHastaInscripcion = fechaHastaInscripcion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sesion other = (Sesion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sesion [id=" + id + ", fechaDesde=" + fechaDesde + ", fechaHasta=" + fechaHasta + ", cupos=" + cupos
				+ ", costo=" + costo + ", curso=" + curso + ", fechaDesdeInscripcion=" + fechaDesdeInscripcion
				+ ", fechaHastaInscripcion=" + fechaHastaInscripcion + "]";
	}
}
