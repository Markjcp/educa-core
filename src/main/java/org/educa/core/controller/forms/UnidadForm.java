package org.educa.core.controller.forms;

import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.Unidad;

public class UnidadForm {

	private Curso curso;
	
	private boolean publicado;
	
	private Unidad unidad;

	public UnidadForm() {
		super();
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public boolean isPublicado() {
		return publicado;
	}

	public void setPublicado(boolean publicado) {
		this.publicado = publicado;
	}

	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}
	
}
