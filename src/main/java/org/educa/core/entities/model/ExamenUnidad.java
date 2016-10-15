package org.educa.core.entities.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "examen_unidad")
public class ExamenUnidad implements Serializable {

	private static final long serialVersionUID = 7264421509489670365L;

	@EmbeddedId
	private ExamenUnidadId id;

	@Column(name = "cant_preguntas_alumno")
	private Integer cantPreguntasUsuario;

	@JsonIgnore
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "numero_componente", referencedColumnName = "numero_componente", insertable = false, updatable = false),
			@JoinColumn(name = "id_curso", referencedColumnName = "id_curso", insertable = false, updatable = false) })
	private Unidad unidad;

	@OneToMany(cascade = { CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumns({ @JoinColumn(name = "numero_componente",referencedColumnName = "numero_componente", insertable = false, updatable = false),
			@JoinColumn(name = "id_curso", referencedColumnName = "id_curso", insertable = false, updatable = false),
			@JoinColumn(name = "numero_examen", referencedColumnName= "numero_examen", insertable = false, updatable = false) })
	private List<PreguntaExamenUnidad> preguntas;

	public ExamenUnidadId getId() {
		return id;
	}

	public void setId(ExamenUnidadId id) {
		this.id = id;
	}

	public Integer getCantPreguntasUsuario() {
		return cantPreguntasUsuario;
	}

	public void setCantPreguntasUsuario(Integer cantPreguntasUsuario) {
		this.cantPreguntasUsuario = cantPreguntasUsuario;
	}

	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}

	public List<PreguntaExamenUnidad> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<PreguntaExamenUnidad> preguntas) {
		this.preguntas = preguntas;
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
		ExamenUnidad other = (ExamenUnidad) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
