package org.educa.core.entities.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ExamenUnidadId implements Serializable{
	
	private static final long serialVersionUID = 7725754011320558929L;

	@Column(name = "numero_componente")
	private Integer numero;

	@Column(name = "id_curso")
	private Long idCurso;
	
	@Column(name = "numero_examen")
	private Integer idExamen; //Asumo que es el id de la unidad NO ES ASI, ES EL DEL EXAMEN

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

	public Integer getIdExamen() {
		return idExamen;
	}

	public void setIdExamen(Integer idExamen) {
		this.idExamen = idExamen;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCurso == null) ? 0 : idCurso.hashCode());
		result = prime * result + ((idExamen == null) ? 0 : idExamen.hashCode());
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
		ExamenUnidadId other = (ExamenUnidadId) obj;
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
		return true;
	}
}
