package org.educa.core.entities.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
	
	@JsonIgnore
	@OneToMany
	@JoinColumns({
		@JoinColumn(name = "numero_componente", referencedColumnName = "numero_componente", insertable = false, updatable = false),
		@JoinColumn(name = "id_curso", referencedColumnName = "id_curso", insertable = false, updatable = false)		
	})
	private List<ExamenUnidad> examenes;
	
	@JsonIgnore
	@OneToMany
	@JoinColumns({
		@JoinColumn(name = "numero_componente", referencedColumnName = "numero_componente", insertable = false, updatable = false),
		@JoinColumn(name = "id_curso", referencedColumnName = "id_curso", insertable = false, updatable = false)		
	})
	private List<VideoUnidad> videos;
	
	@JsonIgnore
	@OneToMany
	@JoinColumns({
		@JoinColumn(name = "numero_componente", referencedColumnName = "numero_componente", insertable = false, updatable = false),
		@JoinColumn(name = "id_curso", referencedColumnName = "id_curso", insertable = false, updatable = false)		
	})
	private List<MaterialUnidad> material;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "estado_unidad")
	@JsonIgnore
	private Estado estadoUnidad;

	public Unidad() {
		super();
		this.estadoUnidad = Estado.NO_PUBLICADO;
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
	
	public List<ExamenUnidad> getExamenes() {
		return examenes;
	}

	public void setExamenes(List<ExamenUnidad> examenes) {
		this.examenes = examenes;
	}

	public List<VideoUnidad> getVideos() {
		return videos;
	}

	public void setVideos(List<VideoUnidad> videos) {
		this.videos = videos;
	}

	public List<MaterialUnidad> getMaterial() {
		return material;
	}

	public void setMaterial(List<MaterialUnidad> material) {
		this.material = material;
	}
	
	public void addMaterial(MaterialUnidad material) {
		if(this.material == null){
			this.material = new ArrayList<MaterialUnidad>();
		}
		
		this.material.add(material);
	}
	
	public void addVideo(VideoUnidad video) {
		if(this.videos == null){
			this.videos = new ArrayList<VideoUnidad>();
		}
		
		this.videos.add(video);
	}

	public Estado getEstadoUnidad() {
		return estadoUnidad;
	}

	public void setEstadoUnidad(Estado estadoUnidad) {
		this.estadoUnidad = estadoUnidad;
	}
	
	public boolean isPublicado() {
		return (Estado.PUBLICADO.equals(this.estadoUnidad) ? true : false);
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
				+ ", duracionEstimada=" + duracionEstimada + ", examenes=" + examenes + ", videos=" + videos
				+ ", material=" + material + ", estadoUnidad=" + estadoUnidad + "]";
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
