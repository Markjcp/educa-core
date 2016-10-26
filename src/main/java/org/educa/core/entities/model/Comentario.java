package org.educa.core.entities.model;

import java.io.Serializable;
import java.util.Date;

public class Comentario implements Serializable, Comparable<Comentario> {

	private static final long serialVersionUID = -2876809698536532181L;
	
	private Date fechaCreacion;
	private Usuario usuario;
	private String descripcion;
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

	@Override
	public int compareTo(Comentario o) {
		return getFechaCreacion().compareTo(o.getFechaCreacion());
	}
}
