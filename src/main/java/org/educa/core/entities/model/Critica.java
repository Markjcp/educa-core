package org.educa.core.entities.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "critica")
public class Critica implements Serializable {

	private static final long serialVersionUID = 1874703052604943872L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_critica")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
	private Curso curso;

	@Column(name = "fecha")
	private Date fecha;

	@Column(name = "calificacion")
	private Integer calificacion;

	@Column(name = "comentario")
	private String comentario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Integer calificacion) {
		this.calificacion = calificacion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
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
		Critica other = (Critica) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Critica [id=" + id + ", curso=" + curso + ", fecha=" + fecha + ", calificacion=" + calificacion
				+ ", comentario=" + comentario + "]";
	}

}
