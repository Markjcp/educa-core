package org.educa.core.controller.forms;

import javax.validation.Valid;

import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.Unidad;

public class CursoForm {

	@Valid
	private Curso curso;
	
	private boolean editar;
	
	@Valid
	private Unidad nuevaUnidad;	
	
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
}
