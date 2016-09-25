package org.educa.core.entities.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.educa.core.entities.Persistible;

@Entity
@Table(name = "docente")
public class Docente implements Persistible {

	private static final long serialVersionUID = -5104996644735951101L;

	@Id
	@Column(name = "legajo")
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
	private Usuario usuario;

	@Transient
	private String identificacion;

	public Docente() {
		super();
		this.identificacion = "";
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getIdentificacion() {
		if (this.usuario != null && (this.identificacion == null || this.identificacion.isEmpty())) {
			this.identificacion = this.getUsuario().getNombre() + " " + this.getUsuario().getApellido() + " ("
					+ this.getUsuario().getEmail() + ")";
		}

		return this.identificacion;
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
		Docente other = (Docente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Docente [id=" + id + ", usuario=" + usuario + "]";
	}

}
