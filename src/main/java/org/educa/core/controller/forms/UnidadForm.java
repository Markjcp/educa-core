package org.educa.core.controller.forms;

import java.util.List;

import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.ExamenUnidad;
import org.educa.core.entities.model.Unidad;
import org.springframework.web.multipart.MultipartFile;

public class UnidadForm {

	private Curso curso;	
	private boolean publicado;	
	private Unidad unidad;
	
	//Material teorico
	private String materialTeorico;
	//Fin - Material teorico	
	
	//Examen
	private ExamenUnidad examenUnidad;
	private boolean opcionUnoSeleccionada;
	private boolean opcionDosSeleccionada;
	private boolean opcionTresSeleccionada;
	private boolean opcionCuatroSeleccionada;	
	private String respuestaOpcionUno;
	private String respuestaOpcionDos;
	private String respuestaOpcionTres;
	private String respuestaOpcionCuatro;	
	private boolean multipleChoice;	
	private String respuestaUnica;	
	private String pregunta;	
	private Integer cantidadPreguntasAlumno;
	
	private List<PreguntasForm> preguntas;
	//Fin - Examen
	
	//Video
	private MultipartFile video;
	private String videoBytes;
	private boolean edicion;
	private List<String> errores;
	//Fin - video

	public UnidadForm() {
		super();		
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public boolean isPublicado() {
		return publicado;
	}

	public void setPublicado(boolean publicado) {
		this.publicado = publicado;
	}

	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}

	public boolean isOpcionUnoSeleccionada() {
		return opcionUnoSeleccionada;
	}

	public void setOpcionUnoSeleccionada(boolean opcionUnoSeleccionada) {
		this.opcionUnoSeleccionada = opcionUnoSeleccionada;
	}

	public boolean isOpcionDosSeleccionada() {
		return opcionDosSeleccionada;
	}

	public void setOpcionDosSeleccionada(boolean opcionDosSeleccionada) {
		this.opcionDosSeleccionada = opcionDosSeleccionada;
	}

	public boolean isOpcionTresSeleccionada() {
		return opcionTresSeleccionada;
	}

	public void setOpcionTresSeleccionada(boolean opcionTresSeleccionada) {
		this.opcionTresSeleccionada = opcionTresSeleccionada;
	}

	public boolean isOpcionCuatroSeleccionada() {
		return opcionCuatroSeleccionada;
	}

	public void setOpcionCuatroSeleccionada(boolean opcionCuatroSeleccionada) {
		this.opcionCuatroSeleccionada = opcionCuatroSeleccionada;
	}

	public String getRespuestaOpcionUno() {
		return respuestaOpcionUno;
	}

	public void setRespuestaOpcionUno(String respuestaOpcionUno) {
		this.respuestaOpcionUno = respuestaOpcionUno;
	}

	public String getRespuestaOpcionDos() {
		return respuestaOpcionDos;
	}

	public void setRespuestaOpcionDos(String respuestaOpcionDos) {
		this.respuestaOpcionDos = respuestaOpcionDos;
	}

	public String getRespuestaOpcionTres() {
		return respuestaOpcionTres;
	}

	public void setRespuestaOpcionTres(String respuestaOpcionTres) {
		this.respuestaOpcionTres = respuestaOpcionTres;
	}

	public String getRespuestaOpcionCuatro() {
		return respuestaOpcionCuatro;
	}

	public void setRespuestaOpcionCuatro(String respuestaOpcionCuatro) {
		this.respuestaOpcionCuatro = respuestaOpcionCuatro;
	}

	public String getRespuestaUnica() {
		return respuestaUnica;
	}

	public void setRespuestaUnica(String respuestaUnica) {
		this.respuestaUnica = respuestaUnica;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public boolean isMultipleChoice() {
		return multipleChoice;
	}

	public void setMultipleChoice(boolean multipleChoice) {
		this.multipleChoice = multipleChoice;
	}

	public Integer getCantidadPreguntasAlumno() {
		return cantidadPreguntasAlumno;
	}

	public void setCantidadPreguntasAlumno(Integer cantidadPreguntasAlumno) {
		this.cantidadPreguntasAlumno = cantidadPreguntasAlumno;
	}

	public String getMaterialTeorico() {
		return materialTeorico;
	}

	public void setMaterialTeorico(String materialTeorico) {
		this.materialTeorico = materialTeorico;
	}

	public MultipartFile getVideo() {
		return video;
	}

	public void setVideo(MultipartFile video) {
		this.video = video;
	}

	public String getVideoBytes() {
		return videoBytes;
	}

	public void setVideoBytes(String videoBytes) {
		this.videoBytes = videoBytes;
	}

	public boolean isEdicion() {
		return edicion;
	}

	public void setEdicion(boolean edicion) {
		this.edicion = edicion;
	}

	public ExamenUnidad getExamenUnidad() {
		return examenUnidad;
	}

	public void setExamenUnidad(ExamenUnidad examenUnidad) {
		this.examenUnidad = examenUnidad;
	}

	public List<String> getErrores() {
		return errores;
	}

	public void setErrores(List<String> errores) {
		this.errores = errores;
	}

	public List<PreguntasForm> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<PreguntasForm> preguntas) {
		this.preguntas = preguntas;
	}	
}
