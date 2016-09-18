package org.educa.core.entities.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UnidadSesionId implements Serializable{

	private static final long serialVersionUID = 8024718681787518432L;
	
	@Column(name = "numero_unidad")
	private Integer numero;

	@Column(name = "id_curso")
	private Long idCurso;
	
	@Column(name = "id_sesion")
	private Long idSesion;

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

	public Long getIdSesion() {
		return idSesion;
	}

	public void setIdSesion(Long idSesion) {
		this.idSesion = idSesion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCurso == null) ? 0 : idCurso.hashCode());
		result = prime * result + ((idSesion == null) ? 0 : idSesion.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
		UnidadSesionId other = (UnidadSesionId) obj;
		if (idCurso == null) {
			if (other.idCurso != null)
				return false;
		} else if (!idCurso.equals(other.idCurso))
			return false;
		if (idSesion == null) {
			if (other.idSesion != null)
				return false;
		} else if (!idSesion.equals(other.idSesion))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UnidadSesionId [numero=" + numero + ", idCurso=" + idCurso + ", idSesion=" + idSesion + "]";
	}
}
