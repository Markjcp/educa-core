package org.educa.core.entities.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "comentario")
public class Comentario implements Serializable, Comparable<Comentario> {

	private static final long serialVersionUID = -2876809698536532181L;

	@Id
	@Column(name = "id_comentario")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "fecha_creacion")
	private Date fechaCreacion;

	@ManyToOne
	@JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", insertable = false, updatable = false)
	private Usuario usuario;
	
	@Column(name = "id_usuario")
	private Long idUsuario;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_tema", referencedColumnName = "id_tema", insertable = false, updatable = false)
	private Tema tema;
	
	@Column(name = "id_tema")
	private Long idTema;

	@Column(name = "descripcion")
	private String descripcion;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_comentario")
	private EstadoPublicacion estado;

	public Comentario() {
		super();
		this.estado = EstadoPublicacion.INDEFINIDO;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public EstadoPublicacion getEstado() {
		return estado;
	}

	public void setEstado(EstadoPublicacion estado) {
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int compareTo(Comentario o) {
		int resultado = getFechaCreacion().compareTo(o.getFechaCreacion());
		if (resultado == 0 && usuario!= null && o.getUsuario() != null) {
			resultado = usuario.getId().compareTo(o.getUsuario().getId());
		}
		if(resultado == 0 && id != null && o.getId() != null){
			resultado = id.compareTo(o.getId());
		}
		return resultado;
	}
	
	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Long getIdTema() {
		return idTema;
	}

	public void setIdTema(Long idTema) {
		this.idTema = idTema;
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
		Comentario other = (Comentario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
