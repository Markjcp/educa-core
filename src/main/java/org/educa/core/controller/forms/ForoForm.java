package org.educa.core.controller.forms;

import javax.validation.Valid;

import org.educa.core.entities.model.Comentario;
import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.Sesion;
import org.educa.core.entities.model.Tema;

public class ForoForm {

	@Valid
	private Curso curso;
	
	private Sesion sesion;
	
	@Valid
	private Tema tema;
	
	@Valid
	private Comentario comentario;

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

	public Sesion getSesion() {
		return sesion;
	}

	public void setSesion(Sesion sesion) {
		this.sesion = sesion;
	}

	public Comentario getComentario() {
		return comentario;
	}

	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
	}	
}
