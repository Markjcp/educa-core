package org.educa.core.entities.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.educa.core.entities.Persistible;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "curso")
public class Curso implements Persistible {

	private static final long serialVersionUID = 312187995114943044L;

	@Id
	@GeneratedValue
	private Long id;
	
	@NotEmpty(message="Debe ingresar un código único.")
	@Column(nullable = false, columnDefinition = "VARCHAR(10)", unique = true)
	private String codigo;
		
	@NotEmpty(message="Debe ingresar un nombre.")
	@Column(nullable = false, columnDefinition = "VARCHAR(20)")
	private String nombre;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull(message="Debe ingresar una categoría.")
	//@JoinColumn(name = "curso_categoria", nullable = false)	
	private Category categoria;
		
	@Column(columnDefinition = "VARCHAR(150)")
	private String descripcion;
	
	private String imagen; //TODO [ediaz] ESTO HAY Q DEFINIR BIEN
	
	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull(message="Debe asignarselo a un profesor.")
	//@JoinColumn(name = "curso_profesor", nullable = false)
	private Profesor profesor;
		
	//private List<Profesor> ayudantes;  TODO [ediaz] ESTO HAY Q VER SI SON TODOS PROFESORES O SI SON PUNTUALES AYUDANTES (OTRA ENTIDAD)
	
	public Curso() {
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Category getCategoria() {
		return categoria;
	}

	public void setCategoria(Category categoria) {
		this.categoria = categoria;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	@Override
	public String toString() {
		return "Curso [id=" + id + ", codigo=" + codigo + ", nombre=" + nombre + ", categoria=" + categoria
				+ ", descripcion=" + descripcion + ", imagen=" + imagen + ", profesor=" + profesor + "]";
	}	
}
