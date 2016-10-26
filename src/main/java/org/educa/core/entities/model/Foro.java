package org.educa.core.entities.model;

import java.io.Serializable;
import java.util.SortedSet;

public class Foro implements Serializable {

	private static final long serialVersionUID = 2219893730029361018L;
	
	private EstadoForo estado;
	private SortedSet<Tema> temas;
	
	public Foro() {
		super();
	}

	public EstadoForo getEstado() {
		return estado;
	}

	public void setEstado(EstadoForo estado) {
		this.estado = estado;
	}

	public SortedSet<Tema> getTemas() {
		return temas;
	}

	public void setTemas(SortedSet<Tema> temas) {
		this.temas = temas;
	}	
}
