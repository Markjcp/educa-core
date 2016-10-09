package org.educa.core.entities.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "material_unidad")
public class MaterialUnidad implements Serializable {

	private static final long serialVersionUID = -1446011365980301301L;

	@EmbeddedId
	private MaterialUnidadId id;

	@Column(name = "material")
	private byte[] material;
	
	@Column(name = "numero_componente", insertable = false, updatable = false)
	private Integer numero;

	@Column(name = "id_curso", insertable = false, updatable = false)
	private Long idCurso;	

	public MaterialUnidadId getId() {
		return id;
	}

	public void setId(MaterialUnidadId id) {
		this.id = id;
	}

	public byte[] getMaterial() {
		return material;
	}

	public void setMaterial(byte[] material) {
		this.material = material;
	}
	
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
		MaterialUnidad other = (MaterialUnidad) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
