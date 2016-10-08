package org.educa.core.entities.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "opcion_examen_unidad")
public class OpcionExamenUnidad implements Serializable {

	private static final long serialVersionUID = 3316739889980347510L;

	@EmbeddedId
	private OpcionExamenUnidadId id;

	@Column(name = "texto")
	private String texto;

	@Column(name = "es_correcta")
	private boolean esCorrecta;

	public OpcionExamenUnidadId getId() {
		return id;
	}

	public void setId(OpcionExamenUnidadId id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public boolean isEsCorrecta() {
		return esCorrecta;
	}

	public void setEsCorrecta(boolean esCorrecta) {
		this.esCorrecta = esCorrecta;
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
		OpcionExamenUnidad other = (OpcionExamenUnidad) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
