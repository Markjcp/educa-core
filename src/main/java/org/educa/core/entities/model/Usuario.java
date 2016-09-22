package org.educa.core.entities.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import org.educa.core.entities.Persistible;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "usuario")
public class Usuario implements Persistible {

	private static final long serialVersionUID = -3889488130577789067L;

	@Id
	@Column(name = "id_usuario")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "nombre")
	@NotEmpty(message = "Debe ingresar un nombre.")
	@Length(max=50, message="Longitud máxima de 50 caracteres.")
	@Pattern(regexp="[ a-zA-ZáéíóúÁÉÍÓÚÑñ]*", message = "Nombre inválido")
	private String nombre;

	@Column(name = "apellido")
	@NotEmpty(message = "Debe ingresar un apellido.")
	@Length(max=50, message="Longitud máxima de 50 caracteres.")
	@Pattern(regexp="[ a-zA-ZáéíóúÁÉÍÓÚÑñ]*", message = "Apellido inválido")
	private String apellido;

	@Column(name = "email")
	@NotEmpty(message = "Debe ingresar un email.")
	@Length(max=50, message="Longitud máxima de 50 caracteres.")
	@Pattern(regexp="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", message = "Email inválido")
	private String email;

	@Column(name = "foto")
	private byte[] foto;

	@ManyToOne
	@JoinColumn(name = "id_rol_usuario", referencedColumnName = "id_rol_usuario")
	private RolUsuario rol;

	@Column(name = "password")
	@NotEmpty(message = "Debe ingresar el password.")
	@Length(min=8,max=50, message="La contraseña debe ser de entre 8 y 50 caracteres.")
	private String password;
	
	@Transient
	@NotEmpty(message = "Debe confirmar el password.")
	private String passwordConfirmacion;

	@Column(name = "clave_activacion")
	private String claveActivacion;

	@Column(name = "id_facebook")
	private String idFacebook;

	@Column(name = "id_google")
	private String idGoogle;
	
	@Column(name = "activado")
	private Boolean activado;

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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public RolUsuario getRol() {
		return rol;
	}

	public void setRol(RolUsuario rol) {
		this.rol = rol;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPasswordConfirmacion() {
		return passwordConfirmacion;
	}

	public void setPasswordConfirmacion(String passwordConfirmacion) {
		this.passwordConfirmacion = passwordConfirmacion;
	}

	public String getClaveActivacion() {
		return claveActivacion;
	}

	public void setClaveActivacion(String claveActivacion) {
		this.claveActivacion = claveActivacion;
	}

	public String getIdFacebook() {
		return idFacebook;
	}

	public void setIdFacebook(String idFacebook) {
		this.idFacebook = idFacebook;
	}

	public String getIdGoogle() {
		return idGoogle;
	}

	public void setIdGoogle(String idGoogle) {
		this.idGoogle = idGoogle;
	}
	
	public Boolean getActivado() {
		return activado;
	}

	public void setActivado(Boolean activado) {
		this.activado = activado;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + ", foto="
				+ Arrays.toString(foto) + "]";
	}
}
