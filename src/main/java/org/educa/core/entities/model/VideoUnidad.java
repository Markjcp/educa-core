package org.educa.core.entities.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.educa.core.entities.constants.ConstantesDelModelo;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "video_unidad")
public class VideoUnidad implements Serializable {

	private static final long serialVersionUID = -5793053295083247926L;

	@EmbeddedId
	private VideoUnidadId id;

	@Column(name = "video")
	private byte[] video;

	@Column(name = "titulo")
	@Length(max=ConstantesDelModelo.MAX_VIDEO_NAME, message="El nombre del video debe tener al menos {max} caracteres.")
	private String titulo;
	
	@Column(name = "numero_componente", insertable = false, updatable = false)
	private Integer numero;

	@Column(name = "id_curso", insertable = false, updatable = false)
	private Long idCurso;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "numero_componente", referencedColumnName = "numero_componente", insertable = false, updatable = false),
		@JoinColumn(name = "id_curso", referencedColumnName = "id_curso", insertable = false, updatable = false)		
	})	
	private Unidad unidad;

	public VideoUnidadId getId() {
		return id;
	}

	public void setId(VideoUnidadId id) {
		this.id = id;
	}

	public byte[] getVideo() {
		return video;
	}

	public void setVideo(byte[] video) {
		this.video = video;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
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
	
	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
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
		VideoUnidad other = (VideoUnidad) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
