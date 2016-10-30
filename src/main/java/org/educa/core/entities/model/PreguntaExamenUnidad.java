package org.educa.core.entities.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pregunta_examen_unidad")
public class PreguntaExamenUnidad implements Serializable {

	private static final long serialVersionUID = 6780971771289604276L;

	@EmbeddedId
	private PreguntaExamenUnidadId id;

	@Column(name = "enunciado")
	private String enunciado;

	@Column(name = "multiple_choice")
	private boolean multipleChoice;

	@Column(name = "respuesta")
	private String respuesta;

	@OneToMany(cascade = { CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumns({
			@JoinColumn(name = "numero_componente", referencedColumnName = "numero_componente", insertable = false, updatable = false),
			@JoinColumn(name = "id_curso", referencedColumnName = "id_curso", insertable = false, updatable = false),
			@JoinColumn(name = "numero_examen", referencedColumnName = "numero_examen", insertable = false, updatable = false),
			@JoinColumn(name = "numero_pregunta", referencedColumnName = "numero_pregunta", insertable = false, updatable = false) })
	private List<OpcionExamenUnidad> opciones;

	public PreguntaExamenUnidadId getId() {
		return id;
	}

	public void setId(PreguntaExamenUnidadId id) {
		this.id = id;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public boolean isMultipleChoice() {
		return multipleChoice;
	}

	public void setMultipleChoica(boolean multipleChoice) {
		this.multipleChoice = multipleChoice;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public List<OpcionExamenUnidad> getOpciones() {
		return opciones;
	}

	public void setOpciones(List<OpcionExamenUnidad> opciones) {
		this.opciones = opciones;
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
		PreguntaExamenUnidad other = (PreguntaExamenUnidad) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public boolean esOpcionValida(Integer idOpcionElegida) {
		boolean correcta = false;
		for (OpcionExamenUnidad opcion : this.getOpciones()) {
			if (opcion.getId().getIdOpcion() == idOpcionElegida 
					&& opcion.isEsCorrecta()) {
				correcta = true;
			}
		}
		return correcta;
	}

}
