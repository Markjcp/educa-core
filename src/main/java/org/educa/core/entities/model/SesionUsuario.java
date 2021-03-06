package org.educa.core.entities.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "sesion_usuario")
public class SesionUsuario implements Serializable {

	private static final long serialVersionUID = 2269107482062483672L;

	@EmbeddedId
	private SesionUsuarioId id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_curso", insertable = false, updatable = false)
	private Curso curso;

	@Column(name = "desaprobado")
	private Boolean desaprobado;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_sesion_usuario")
	private EstadoSesionUsuario estado;

	@JsonIgnore
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "numero_componente", referencedColumnName = "numero_componente", insertable = false, updatable = false),
			@JoinColumn(name = "id_curso", referencedColumnName = "id_curso", insertable = false, updatable = false) })
	private Sesion sesion;

	public SesionUsuarioId getId() {
		return id;
	}

	public void setId(SesionUsuarioId id) {
		this.id = id;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Boolean getDesaprobado() {
		return desaprobado;
	}

	public void setDesaprobado(Boolean desaprobado) {
		this.desaprobado = desaprobado;
	}

	public EstadoSesionUsuario getEstado() {
		return estado;
	}

	public void setEstado(EstadoSesionUsuario estado) {
		this.estado = estado;
	}
	
	public Sesion getSesion() {
		return sesion;
	}

	public void setSesion(Sesion sesion) {
		this.sesion = sesion;
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
		SesionUsuario other = (SesionUsuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
