package org.educa.core.entities.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.educa.core.entities.Persistible;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "curso")
public class Curso implements Persistible {

	private static final long serialVersionUID = 312187995114943044L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id_curso")
	private Long id;

	@NotEmpty(message = "Debe ingresar un nombre.")
	@Pattern(regexp="[ a-zA-Z0-9áéíóúÁÉÍÓÚ]*", message = "Deben ser letras o números")
	@Column(name = "nombre", nullable=false)
	@Length(max=100, message="Longitud máxima de 100 caracteres alfanuméricos.")
	private String nombre;

	@ManyToOne
	@NotNull(message = "Debe ingresar una categoría.")
	@JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria", nullable = false)	
	private Categoria categoria;
	
	@Column(name="id_categoria", insertable = false, updatable = false)
	private Long categoriaId;

	@NotEmpty(message = "Debe ingresar una descripción.")
	@Column(name = "descripcion")
	@Length(max=255, message="Longitud máxima de 255 caracteres.")
	private String descripcion;

	@JsonIgnore
	@Column(name = "imagen")
	private byte[] imagen;
	
	@Column(name = "link_imagen")
	private String linkImagen;

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
	
	@OneToMany
	@JoinColumn(name="id_curso")
	private List<Sesion> sesiones;
	
	@OneToMany
	@JoinColumn(name="id_curso")
	private List<Unidad> unidades;
	
	@OneToMany
	@JoinColumn(name="id_curso")
	private List<Critica> criticas;
	
	@Transient
	private MultipartFile foto;

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
	
	public String getLinkImagen() {
		return linkImagen;
	}

	public void setLinkImagen(String linkImagen) {
		this.linkImagen = linkImagen;
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
	
	public MultipartFile getFoto() {
		return foto;
	}

	public void setFoto(MultipartFile foto) {
		this.foto = foto;
	}
	
	public List<Sesion> getSesiones() {
		return sesiones;
	}

	public void setSesiones(List<Sesion> sesiones) {
		this.sesiones = sesiones;
	}

	public List<Unidad> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidad> unidades) {
		this.unidades = unidades;
	}

	public List<Critica> getCriticas() {
		return criticas;
	}

	public void setCriticas(List<Critica> criticas) {
		this.criticas = criticas;
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
