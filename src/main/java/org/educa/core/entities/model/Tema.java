package org.educa.core.entities.model;

import java.io.Serializable;
import java.util.Date;
import java.util.SortedSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OrderBy;

@Entity
@Table(name = "tema")
public class Tema implements Serializable, Comparable<Tema> {

	private static final long serialVersionUID = 3817141123714789306L;

	@Id
	@Column(name = "id_tema")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "fecha_creacion")
	private Date fechaCreacion;

	@ManyToOne
	@JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", insertable = false, updatable = false)
	private Usuario usuario;
	
	@Column(name = "id_usuario")
	private Long idUsuario;
	
	@Column(name = "id_foro")
	private Long idForo;

	@Column(name = "titulo") //TODO largo = 45 obligatorio
	private String titulo;

	@Column(name = "descripcion")  //TODO largo = 200 obligatorio
	private String descripcion;

	@OneToMany
	@JoinColumn(name = "id_tema", referencedColumnName= "id_tema",insertable = false, updatable = false)
	@OrderBy(clause = "fechaCreacion asc")
	private SortedSet<Comentario> comentarios;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_tema")
	private EstadoPublicacion estado;
	
	//TODO VER SI ESTO LO AGREGAMOS A LOS CAMPOS DE LA BASE - seria ideal q este en la base y q con cada add de cosas se actualice automaticamente
	@Transient
	private int cantidadComentariosPorAprobar = 20;

	public Tema() {
		super();
		// this.estado = EstadoPublicacion.INDEFINIDO; TODO comento esto porque
		// no va a estar bueno para hibernate hacerlo en el constuctor por
		// defecto
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
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

	public SortedSet<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(SortedSet<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public EstadoPublicacion getEstado() {
		return estado;
	}

	public void setEstado(EstadoPublicacion estado) {
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getIdForo() {
		return idForo;
	}

	public void setIdForo(Long idForo) {
		this.idForo = idForo;
	}
	
	public boolean isAprobado(){
		return (EstadoPublicacion.APROBADO.equals(getEstado()));
	}
	
	public int getCantidadComentariosPorAprobar() {
		return cantidadComentariosPorAprobar;
	}

	public void setCantidadComentariosPorAprobar(int cantidadComentariosPorAprobar) {
		this.cantidadComentariosPorAprobar = cantidadComentariosPorAprobar;
	}

	@Override
	public int compareTo(Tema o) {
		if(getFechaCreacion() == null && o.getFechaCreacion() != null){
			return -1;
		}
		
		if(getFechaCreacion() == null && o.getFechaCreacion() == null){
			return 0;
		}
		
		return getFechaCreacion().compareTo(o.getFechaCreacion());
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
		Tema other = (Tema) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
