package org.educa.core.controller.forms;

import javax.validation.Valid;

import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.Tema;

public class ForoForm {

	@Valid
	private Curso curso;
	
	@Valid
	private Tema tema;

	public ForoForm() {
		super();
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}	
}
