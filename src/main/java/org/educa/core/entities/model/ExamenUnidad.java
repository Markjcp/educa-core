package org.educa.core.entities.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "examen_unidad")
public class ExamenUnidad implements Serializable {

	private static final long serialVersionUID = 7264421509489670365L;

	@EmbeddedId
	private ExamenUnidadId id;

	@Column(name = "enunciado")
	private String enunciado;

	@Column(name = "cant_preguntas_alumno")
	private Integer cantPreguntasUsuario;

	@Column(name = "multiple_choice")
	private boolean multipleChoica;

	@Column(name = "respuesta")
	private String respuesta;
		
	public ExamenUnidadId getId() {
		return id;
	}

	public void setId(ExamenUnidadId id) {
		this.id = id;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public Integer getCantPreguntasUsuario() {
		return cantPreguntasUsuario;
	}

	public void setCantPreguntasUsuario(Integer cantPreguntasUsuario) {
		this.cantPreguntasUsuario = cantPreguntasUsuario;
	}

	public boolean isMultipleChoica() {
		return multipleChoica;
	}

	public void setMultipleChoica(boolean multipleChoica) {
		this.multipleChoica = multipleChoica;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
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
