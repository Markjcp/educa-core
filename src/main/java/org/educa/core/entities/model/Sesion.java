package org.educa.core.entities.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.educa.core.util.FechaUtil;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "sesion")
public class Sesion implements Serializable, Comparable<Sesion> {

	private static final long serialVersionUID = -219703370172178413L;
		
	@EmbeddedId	
	private ComponenteId id;
	
	@Column(name = "fecha_desde")
	@NotNull(message = "Debe de ingresar una fecha de inicio de sesión.")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date fechaDesde;
	
	@Column(name = "fecha_hasta")
	@NotNull(message = "Debe de ingresar una fecha de finalización de sesión.")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date fechaHasta;
	
	@Column(name = "cupos")
	private Integer cupos;
	
	@Column(name = "costo")
	private BigDecimal costo;
	
	@ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.PERSIST})
	@JoinColumn(name = "id_curso", referencedColumnName = "id_curso", insertable = false, updatable = false)
	@JsonIgnore
	private Curso curso;
	
	@Column(name = "fecha_desde_inscripcion")
	@NotNull(message = "Debe de ingresar una fecha de inicio de inscripción a la sesión.")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date fechaDesdeInscripcion;
	
	@Column(name = "fecha_hasta_inscripcion")	
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date fechaHastaInscripcion;
	
	@OneToOne
	@JoinColumn(name = "id_foro", referencedColumnName = "id_foro")
	private Foro foro;

	public Sesion() {
		super();
	}

	public ComponenteId getId() {
		return id;
	}

	public void setId(ComponenteId id) {
		this.id = id;
	}

	public Date getFechaDesde() {
		return FechaUtil.formateFechaDDMMYYYYEs(fechaDesde);		
	}

	public void setFechaDesde(Date fechaDesde) {		
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return FechaUtil.formateFechaDDMMYYYYEs(fechaHasta);
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public Integer getCupos() {
		return cupos;
	}

	public void setCupos(Integer cupos) {
		this.cupos = cupos;
	}

	public BigDecimal getCosto() {
		return costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Date getFechaDesdeInscripcion() {
		return FechaUtil.formateFechaDDMMYYYYEs(fechaDesdeInscripcion);
	}

	public void setFechaDesdeInscripcion(Date fechaDesdeInscripcion) {
		this.fechaDesdeInscripcion = fechaDesdeInscripcion;
	}

	public Date getFechaHastaInscripcion() {
		return FechaUtil.formateFechaDDMMYYYYEs(fechaHastaInscripcion);
	}

	public void setFechaHastaInscripcion(Date fechaHastaInscripcion) {
		this.fechaHastaInscripcion = fechaHastaInscripcion;
	}

	public Foro getForo() {
		return foro;
	}

	public void setForo(Foro foro) {
		this.foro = foro;
	}
	
	public String getDescripcionLarga() {		
		return "Sesión Nro. " + (this.getId().getNumero() == null ? "" : this.getId().getNumero())  + ": Inicia el " + FechaUtil.cadenaFechaDDMMYYYYEs(this.fechaDesde);
	}
	
	public String getDescripcionLargaError() {		
		return "Sesión Nro. " + (this.getId().getNumero() == null ? "" : this.getId().getNumero()) ;
	}
	
	public String getDescripcionForo() {
		String estado = "no moderado";
		if(foro != null && EstadoForo.MODERADO.equals(foro.getEstado())){
			estado = "moderado";
		}
		
		return "Sesión Nro. " + (this.getId().getNumero() == null ? "" : this.getId().getNumero()) + ": Foro " + estado;
	}
	
	public String getMensajeEstado(){
		//TODO me falta poner esto [ediaz]
		String estado = "inactiva.";
		if(Calendar.getInstance().getTime().compareTo(getFechaHasta()) <= 0 && Calendar.getInstance().getTime().compareTo(getFechaDesde()) >= 0){
			estado = "activa.";
		} else if(Calendar.getInstance().getTime().compareTo(getFechaDesde()) <= 0 ){
			estado = "sin comenzar.";
		}
		
		return "Sesión " + estado;
	}
	
	public boolean isForoModerado(){
		if(this.getForo() == null){
			return false;
		}
		
		return (EstadoForo.MODERADO.equals(this.getForo().getEstado()));
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
		Sesion other = (Sesion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sesion [id=" + id + ", fechaDesde=" + fechaDesde + ", fechaHasta=" + fechaHasta + ", cupos=" + cupos
				+ ", costo=" + costo + ", curso=" + curso + ", fechaDesdeInscripcion=" + fechaDesdeInscripcion
				+ ", fechaHastaInscripcion=" + fechaHastaInscripcion + "]";
	}

	@Override
	public int compareTo(Sesion unaSesion) {
		if(this.getId() == null){
			return -1;
		}
		
		if(unaSesion == null || unaSesion.getId() == null){
			return 1; 
		}
		
		//Orden ascendente
		return this.getId().getNumero().compareTo(unaSesion.getId().getNumero());
	}
}
