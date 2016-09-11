package org.educa.core.controller.forms;

import javax.validation.Valid;

import org.educa.core.entities.model.Curso;

public class CursoForm {

	@Valid
	private Curso curso;
	//private boolean esEditar;
	
	public CursoForm() {
		super();
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	/*public boolean isEsEditar() {
		return esEditar;
	}

	public void setEsEditar(boolean esEditar) {
		this.esEditar = esEditar;
	}	*/	
}
