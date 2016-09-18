package org.educa.core.entities.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "unidad")
public class Unidad implements Serializable {

	private static final long serialVersionUID = -5474791836068191980L;

	@EmbeddedId
	private UnidadId id;

	@ManyToOne
	@JoinColumn(name = "id_curso", referencedColumnName = "id_curso", insertable = false, updatable = false)
	private Curso curso;

	@Column(name = "titulo")
	private String titulo;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "duracion_estimada")
	private Integer duracionEstimada;

	public UnidadId getId() {
		return id;
	}

	public void setId(UnidadId id) {
		this.id = id;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getDuracionEstimada() {
		return duracionEstimada;
	}

	public void setDuracionEstimada(Integer duracionEstimada) {
		this.duracionEstimada = duracionEstimada;
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
		Unidad other = (Unidad) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Unidad [id=" + id + ", curso=" + curso + ", titulo=" + titulo + ", descripcion=" + descripcion
				+ ", duracionEstimada=" + duracionEstimada + "]";
	}

}
