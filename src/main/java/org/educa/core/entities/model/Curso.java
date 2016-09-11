package org.educa.core.entities.model;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.educa.core.entities.Persistible;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "curso")
public class Curso implements Persistible {

	private static final long serialVersionUID = 312187995114943044L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id_curso")
	private Long id;

	@NotEmpty(message = "Debe ingresar un nombre.")
	@Size(max=100,message = "Debe tener como máximo 100 caracteres")
	@Pattern(regexp="[ a-zA-Z0-9]*", message = "Deben ser letras o números")
	@Column(name = "nombre")
	private String nombre;

	@ManyToOne
	@NotNull(message = "Debe ingresar una categoría.")
	@JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria", nullable = false)
	private Categoria categoria;
	
	@Column(name="id_categoria", insertable = false, updatable = false)
	private Long categoriaId;

	@NotEmpty(message = "Debe ingresar una descripción.")
	@Size(max=255,message = "Debe tener como máximo 255 caracteres")
	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "imagen")
	@NotEmpty(message = "Debe cargar una imágen")
	@NotNull(message = "Debe cargar una imágen")
	private byte[] imagen;

	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull(message = "Debe asignarselo a un docente.")
	@JoinColumn(name = "legajo_docente", referencedColumnName="legajo", nullable = false)
	private Docente docente;

	@Column(name = "valoracion_promedio")
	private Integer valoracionesPromedio;

	@Column(name = "cantidad_valoraciones")
	private Integer cantidadValoraciones; 
	
	@Column(name="fecha_estimada_prox_sesion")
	private Date fechaEstimadaProximaSesion;

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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

	public Integer getValoracionesPromedio() {
		return valoracionesPromedio;
	}

	public void setValoracionesPromedio(Integer valoracionesPromedio) {
		this.valoracionesPromedio = valoracionesPromedio;
	}

	public Integer getCantidadValoraciones() {
		return cantidadValoraciones;
	}

	public void setCantidadValoraciones(Integer cantidadValoraciones) {
		this.cantidadValoraciones = cantidadValoraciones;
	}
	
	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}
	
	public Date getFechaEstimadaProximaSesion() {
		return fechaEstimadaProximaSesion;
	}

	public void setFechaEstimadaProximaSesion(Date fechaEstimadaProximaSesion) {
		this.fechaEstimadaProximaSesion = fechaEstimadaProximaSesion;
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
		Curso other = (Curso) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Curso [id=" + id + ", nombre=" + nombre + ", categoria=" + categoria + ", categoriaId=" + categoriaId
				+ ", descripcion=" + descripcion + ", imagen=" + Arrays.toString(imagen) + ", docente=" + docente
				+ ", valoracionesPromedio=" + valoracionesPromedio + ", cantidadValoraciones=" + cantidadValoraciones
				+ ", fechaEstimadaProximaSesion=" + fechaEstimadaProximaSesion + "]";
	}
}
