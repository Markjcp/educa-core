package org.educa.core.controller.forms;

import javax.validation.Valid;

import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.Sesion;
import org.educa.core.entities.model.Unidad;

public class CursoForm {

	@Valid
	private Curso curso;
	
	private boolean editar;
	
	@Valid
	private Unidad nuevaUnidad;
	
	@Valid
	private Sesion nuevaSesion;
	
	private boolean publicado;
	
	public CursoForm() {
		super();
		editar = false;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public boolean isEditar() {
		return editar;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}

	public Unidad getNuevaUnidad() {
		return nuevaUnidad;
	}

	public void setNuevaUnidad(Unidad nuevaUnidad) {
		this.nuevaUnidad = nuevaUnidad;
	}

	public Sesion getNuevaSesion() {
		return nuevaSesion;
	}

	public void setNuevaSesion(Sesion nuevaSesion) {
		this.nuevaSesion = nuevaSesion;
	}

	public boolean isPublicado() {
		return publicado;
	}

	public void setPublicado(boolean publicado) {
		this.publicado = publicado;
	}	
}
