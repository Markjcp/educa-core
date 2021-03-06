package org.educa.core.entities.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VideoUnidadId implements Serializable{

	private static final long serialVersionUID = -4296574574202773224L;
	
	@Column(name = "numero_componente")
	private Integer numero;

	@Column(name = "id_curso")
	private Long idCurso;
	
	@Column(name = "numero_video")
	private Long idVideo;

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

	public Long getIdVideo() {
		return idVideo;
	}

	public void setIdVideo(Long idVideo) {
		this.idVideo = idVideo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCurso == null) ? 0 : idCurso.hashCode());
		result = prime * result + ((idVideo == null) ? 0 : idVideo.hashCode());
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
		VideoUnidadId other = (VideoUnidadId) obj;
		if (idCurso == null) {
			if (other.idCurso != null)
				return false;
		} else if (!idCurso.equals(other.idCurso))
			return false;
		if (idVideo == null) {
			if (other.idVideo != null)
				return false;
		} else if (!idVideo.equals(other.idVideo))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}
	
	

}
