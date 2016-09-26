package org.educa.core.entities.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "sesion")
public class Sesion implements Serializable, Comparable<Sesion> {

	private static final long serialVersionUID = -219703370172178413L;
		
	@EmbeddedId	
	private ComponenteId id;
	
	@Column(name = "fecha_desde")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date fechaDesde;
	
	@Column(name = "fecha_hasta")
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
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date fechaDesdeInscripcion;
	
	@Column(name = "fecha_hasta_inscripcion")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date fechaHastaInscripcion;

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
		return formateFechaDDMMYYYY(fechaDesde);		
	}

	public void setFechaDesde(Date fechaDesde) {		
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return formateFechaDDMMYYYY(fechaHasta);
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
		return formateFechaDDMMYYYY(fechaDesdeInscripcion);
	}

	public void setFechaDesdeInscripcion(Date fechaDesdeInscripcion) {
		this.fechaDesdeInscripcion = fechaDesdeInscripcion;
	}

	public Date getFechaHastaInscripcion() {
		return formateFechaDDMMYYYY(fechaHastaInscripcion);
	}

	public void setFechaHastaInscripcion(Date fechaHastaInscripcion) {
		this.fechaHastaInscripcion = fechaHastaInscripcion;
	}
	
	public String getDescripcionLarga() {		
		return "Sesión Nro. " + (this.getId().getNumero() == null ? "" : this.getId().getNumero())  + ": Inicia el " + cadenaFechaDDMMYYYY(this.fechaDesde);
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
	
	private Date formateFechaDDMMYYYY(Date fechaBase){
		SimpleDateFormat format = getSimpleDateFormat();
		String cadenaFecha = cadenaFechaDDMMYYYY(fechaBase);		
		Date fecha = null;
		
		try {
			fecha = format.parse(cadenaFecha);
		} catch (Exception ex) {
			//Nada		
		}

		return fecha;
	}
	
	private String cadenaFechaDDMMYYYY(Date fechaBase){
		String fechaDesdeFormateada = "";
		if(fechaBase != null){
			SimpleDateFormat format = getSimpleDateFormat();
			fechaDesdeFormateada = format.format(fechaBase);
		}
		
		return fechaDesdeFormateada;
	}
	
	private SimpleDateFormat getSimpleDateFormat(){
		return new SimpleDateFormat("dd-MM-yyyy");
	}
}