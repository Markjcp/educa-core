package org.educa.core.entities.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.educa.core.entities.Persistible;

@Entity
@Table(name = "profesor")
public class Profesor implements Persistible {
	
	private static final long serialVersionUID = -5104996644735951101L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false, columnDefinition = "VARCHAR(20)")
	private String nombre;
	
	@Column(nullable = false, columnDefinition = "VARCHAR(20)")
	private String email;
	
	public Profesor() {
		super();	
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Profesor [id=" + id + ", nombre=" + nombre + ", email=" + email + "]";
	}
}
