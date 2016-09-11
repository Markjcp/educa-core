package org.educa.core.controller.forms;

import org.educa.core.entities.model.Curso;

public class CursoForm {

	private Curso curso;
	private boolean editar;
	
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
}
