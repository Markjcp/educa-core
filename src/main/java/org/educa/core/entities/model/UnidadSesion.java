package org.educa.core.entities.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "unidad_sesion")
public class UnidadSesion implements Serializable {

	private static final long serialVersionUID = 3715865498210566175L;

	@EmbeddedId
	private UnidadSesionId id;

	@Column(name = "fecha_desde")
	private Date fechaDesde;

	@Column(name = "fecha_hasta")
	private Date fechaHasta;

	public UnidadSesionId getId() {
		return id;
	}

	public void setId(UnidadSesionId id) {
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
		UnidadSesion other = (UnidadSesion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UnidadSesion [id=" + id + ", fechaDesde=" + fechaDesde + ", fechaHasta=" + fechaHasta + "]";
	}

}
