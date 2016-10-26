package org.educa.core.controller.forms;

import javax.validation.Valid;

import org.educa.core.entities.model.Curso;

public class ForoForm {

	@Valid
	private Curso curso;

	public ForoForm() {
		super();
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}	
}
