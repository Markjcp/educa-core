package org.educa.core.entities.model;

import java.io.Serializable;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OrderBy;

@Entity
@Table(name = "foro")
public class Foro implements Serializable {

	private static final long serialVersionUID = 2219893730029361018L;

	@Id
	@Column(name = "id_foro")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_foro")
	private EstadoForo estado;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_foro", referencedColumnName = "id_foro",insertable = false, updatable = false)
	@OrderBy(clause = "fechaCreacion desc")
	private SortedSet<Tema> temas;
	
	@Column(name = "temas_por_aprobar")
	private int cantidadTemasPorAprobar;	
	
	@Column(name = "temas_aprobados")
	private int cantidadTemasAprobados;
	
	@Column(name = "comentarios_por_aprobar")
	private int cantidadComentariosPorAprobar;
	
	@Column(name = "comentarios_aprobados")
	private int cantidadComentariosAprobados;
		
	public Foro() {
		super();
		reiniciarCantidades();
	}

	private void reiniciarCantidades() {
		this.cantidadTemasPorAprobar = 0;	
		this.cantidadTemasAprobados = 0;
		this.cantidadComentariosPorAprobar = 0;
		this.cantidadComentariosAprobados = 0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EstadoForo getEstado() {
		return estado;
	}

	public void setEstado(EstadoForo estado) {
		this.estado = estado;
	}

	public SortedSet<Tema> getTemas() {
		return temas;
	}

	public void setTemas(SortedSet<Tema> temas) {
		this.temas = temas;
	}

	public int getCantidadTemasPorAprobar() {
		return cantidadTemasPorAprobar;
	}

	public void setCantidadTemasPorAprobar(int cantidadTemasPorAprobar) {
		this.cantidadTemasPorAprobar = cantidadTemasPorAprobar;
	}

	public int getCantidadTemasAprobados() {
		return cantidadTemasAprobados;
	}

	public void setCantidadTemasAprobados(int cantidadTemasAprobados) {
		this.cantidadTemasAprobados = cantidadTemasAprobados;
	}

	public int getCantidadComentariosPorAprobar() {
		return cantidadComentariosPorAprobar;
	}

	public void setCantidadComentariosPorAprobar(int cantidadComentariosPorAprobar) {
		this.cantidadComentariosPorAprobar = cantidadComentariosPorAprobar;
	}

	public int getCantidadComentariosAprobados() {
		return cantidadComentariosAprobados;
	}

	public void setCantidadComentariosAprobados(int cantidadComentariosAprobados) {
		this.cantidadComentariosAprobados = cantidadComentariosAprobados;
	}
	
	public boolean isModerado(){
		return EstadoForo.MODERADO.equals(this.getEstado());
	}

	public void addTema(Tema tema) {
		if(tema == null){
			return;
		}
		
		if(this.temas == null){
			this.temas = new TreeSet<Tema>();
		}
		this.temas.add(tema);
		actualizarCantidades(tema);
	}
	
	public boolean actualizarTema(Tema tema){
		boolean encontrado = false;
		if(tema != null){
			for(Tema temaViejo : this.temas){
				if(temaViejo.getId() == tema.getId()){
					encontrado = true;
					this.temas.remove(temaViejo);
					this.temas.add(tema);
					actualizarCantidadesPreexistentes(tema, temaViejo);
					break;
				}
			}
		}
		
		return encontrado;
	}
	
	private void actualizarCantidadesPreexistentes(Tema temaNuevo, Tema temaViejo){
		if(temaNuevo != null && temaViejo != null){
			//Actualizo segun tema viejo (decremento cantidades)
			decrementoCantidades(temaViejo);
			//Actualizo por tema nuevo (aumento cantidades)
			actualizarCantidades(temaNuevo);
		}
	}
	
	private void decrementoCantidades(Tema tema){
		if(tema.isAprobado()){
			this.cantidadTemasAprobados--;
		} else if(!tema.isRechazado()){
			this.cantidadComentariosPorAprobar--;
		}
		
		if(tema.getComentarios() != null && !tema.getComentarios().isEmpty()){
			for(Comentario comentario : tema.getComentarios()){
				if(comentario.isAprobado()){
					this.cantidadComentariosAprobados--;
				} else if(!comentario.isRechazado()) {
					this.cantidadComentariosPorAprobar--;
				}
			}
		}	
	}
	
	private void actualizarCantidades(Tema tema) {
		if(tema != null){
			if(tema.isAprobado()){
				this.cantidadTemasAprobados++;
			} else if(!tema.isRechazado()){
				this.cantidadComentariosPorAprobar++;
			}
			
			if(tema.getComentarios() != null && !tema.getComentarios().isEmpty()){
				for(Comentario comentario : tema.getComentarios()){
					if(comentario.isAprobado()){
						this.cantidadComentariosAprobados++;
					} else if(!comentario.isRechazado()) {
						this.cantidadComentariosPorAprobar++;
					}
				}
			}
		}
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
		Foro other = (Foro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
