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
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
	@JoinColumn(name = "id_foro", insertable = false, updatable = false)
	@OrderBy(clause = "fechaCreacion desc")
	private SortedSet<Tema> temas;
	
	@OneToOne
	@JoinColumns({
		@JoinColumn(name = "numero_componente", referencedColumnName = "numero_componente"),
		@JoinColumn(name = "id_curso", referencedColumnName = "id_curso" )
	})
	private Sesion sesion;

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
	
	public Sesion getSesion() {
		return sesion;
	}

	public void setSesion(Sesion sesion) {
		this.sesion = sesion;
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
