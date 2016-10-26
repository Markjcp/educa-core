package org.educa.core.entities.model;

import java.io.Serializable;
import java.util.Date;
import java.util.SortedSet;

public class Tema implements Serializable, Comparable<Tema> {

	private static final long serialVersionUID = 3817141123714789306L;
	
	private Date fechaCreacion;
	private Usuario usuario;
	private String titulo;
	private String descripcion;//TODO ver si esto va o no
	private SortedSet<Comentario> comentarios;
	private EstadoPublicacion estado;
		
	public Tema() {
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

	public SortedSet<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(SortedSet<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public EstadoPublicacion getEstado() {
		return estado;
	}

	public void setEstado(EstadoPublicacion estado) {
		this.estado = estado;
	}

	@Override
	public int compareTo(Tema o) {
		// TODO Auto-generated method stub
		return getFechaCreacion().compareTo(o.getFechaCreacion());
	}

}
