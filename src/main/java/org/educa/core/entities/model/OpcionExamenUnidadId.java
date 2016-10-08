package org.educa.core.entities.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OpcionExamenUnidadId implements Serializable {

	private static final long serialVersionUID = 1750067432806405816L;

	@Column(name = "numero_componente")
	private Integer numero;

	@Column(name = "id_curso")
	private Long idCurso;

	@Column(name = "numero_examen")
	private Long idExamen;

	@Column(name = "orden")
	private Integer orden;

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Long getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Long idCurso) {
		this.idCurso = idCurso;
	}

	public Long getIdExamen() {
		return idExamen;
	}

	public void setIdExamen(Long idExamen) {
		this.idExamen = idExamen;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCurso == null) ? 0 : idCurso.hashCode());
		result = prime * result + ((idExamen == null) ? 0 : idExamen.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((orden == null) ? 0 : orden.hashCode());
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
		OpcionExamenUnidadId other = (OpcionExamenUnidadId) obj;
		if (idCurso == null) {
			if (other.idCurso != null)
				return false;
		} else if (!idCurso.equals(other.idCurso))
			return false;
		if (idExamen == null) {
			if (other.idExamen != null)
				return false;
		} else if (!idExamen.equals(other.idExamen))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (orden == null) {
			if (other.orden != null)
				return false;
		} else if (!orden.equals(other.orden))
			return false;
		return true;
	}

}
