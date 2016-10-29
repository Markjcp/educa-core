package org.educa.core.entities.model;

import java.io.Serializable;
import java.util.SortedSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OrderBy;

@Entity
@Table(name = "foro")
public class Foro implements Serializable {

	private static final long serialVersionUID = 2219893730029361018L;

	@Id
	@Column(name = "id_foro")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_foro")
	private EstadoForo estado;

	@OneToMany
	@JoinColumn(name = "id_foro", referencedColumnName = "id_foro",insertable = false, updatable = false)
	@OrderBy(clause = "fechaCreacion desc")
	private SortedSet<Tema> temas;
	
	//TODO VER SI ESTO LO AGREGAMOS A LOS CAMPOS DE LA BASE - seria ideal q este en la base y q con cada add de cosas se actualice automaticamente
	@Transient
	private int cantidadTemasPorAprobar = 5;	
	@Transient
	private int cantidadTemasAprobados = 10;
	@Transient
	private int cantidadComentariosPorAprobar = 6;
	@Transient
	private int cantidadComentariosAprobados = 12;
	
	public Foro() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public int getCantidadTemasPorAprobar() {
		return cantidadTemasPorAprobar;
	}

	public void setCantidadTemasPorAprobar(int cantidadTemasPorAprobar) {
		this.cantidadTemasPorAprobar = cantidadTemasPorAprobar;
	}

	public int getCantidadTemasAprobados() {
		return cantidadTemasAprobados;
	}

	public void setCantidadTemasAprobados(int cantidadTemasAprobados) {
		this.cantidadTemasAprobados = cantidadTemasAprobados;
	}

	public int getCantidadComentariosPorAprobar() {
		return cantidadComentariosPorAprobar;
	}

	public void setCantidadComentariosPorAprobar(int cantidadComentariosPorAprobar) {
		this.cantidadComentariosPorAprobar = cantidadComentariosPorAprobar;
	}

	public int getCantidadComentariosAprobados() {
		return cantidadComentariosAprobados;
	}

	public void setCantidadComentariosAprobados(int cantidadComentariosAprobados) {
		this.cantidadComentariosAprobados = cantidadComentariosAprobados;
	}
	
	public boolean isModerado(){
		return EstadoForo.MODERADO.equals(this.getEstado());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Foro other = (Foro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
