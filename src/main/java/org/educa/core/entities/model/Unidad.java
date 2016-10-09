package org.educa.core.entities.model;

import java.io.Serializable;
import java.util.List;
import java.util.SortedSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OrderBy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "unidad")
public class Unidad implements Serializable, Comparable<Unidad> {

	private static final long serialVersionUID = -5474791836068191980L;

	@EmbeddedId	
	private ComponenteId id;

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST})
	@JoinColumn(name = "id_curso", referencedColumnName = "id_curso", insertable = false, updatable = false)
	@JsonIgnore
	private Curso curso;

	@Column(name = "titulo")
	@NotEmpty(message = "Debe ingresar un título.")
	@Length(max=100, message="La longitud máxima para el título es de {max} caracteres.")
	private String titulo;

	@Column(name = "descripcion")
	@NotEmpty(message = "Debe ingresar una descripción.")
	@Length(max=255, message="La longitud máxima para la descripción es de {max} caracteres.")
	private String descripcion;

	@Column(name = "duracion_estimada")
	@NotNull(message = "Debe ingresar una duración estimada.")
	@Min(value = 1, message = "Debe de ingresar un número entero mayor a 0.")
	@Max(value = 9999, message = "La duración estimada no puede ser mayor a {value} horas.")	
	private Integer duracionEstimada;
	
/*	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST})
	@JoinColumn(name = "id_examen_unidad", referencedColumnName = "id", insertable = false, updatable = false)
	@JsonIgnore //TODO VER SI HAY Q IGNORARLO O NO - VER CON MOBILE
	private ExamenUnidad examenUnidad;
	
	//private List<VideoUnidad> videos;
	
	@OneToMany(cascade = {CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.PERSIST})
	@JoinColumn(name="id_curso")
	@OrderBy(clause = "id.numero ASC")
	private SortedSet<VideoUnidad> videos;*/

	public Unidad() {
		super();
	}

	public ComponenteId getId() {
		return id;
	}

	public void setId(ComponenteId id) {
		this.id = id;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getDuracionEstimada() {
		return duracionEstimada;
	}

	public void setDuracionEstimada(Integer duracionEstimada) {
		this.duracionEstimada = duracionEstimada;
	}
	
	public String getDescripcionLarga() {
		return "Unidad Nro. " + (getId().getNumero() == null ? "" : getId().getNumero())  + ": " + getTitulo();
	}
	
	public String getDescripcionLargaError() {
		return "Unidad Nro. " + (getId().getNumero() == null ? "" : getId().getNumero());
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
		Unidad other = (Unidad) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Unidad [id=" + id + ", curso=" + curso + ", titulo=" + titulo + ", descripcion=" + descripcion
				+ ", duracionEstimada=" + duracionEstimada + "]";
	}

	@Override
	public int compareTo(Unidad unaUnidad) {
		if(this.getId() == null){
			return -1;
		}
		
		if(unaUnidad == null || unaUnidad.getId() == null){
			return 1; 
		}
		
		//Orden ascendente
		return this.getId().getNumero().compareTo(unaUnidad.getId().getNumero());
	}

}
