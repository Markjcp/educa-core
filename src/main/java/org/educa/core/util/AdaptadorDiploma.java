package org.educa.core.util;

import java.text.SimpleDateFormat;

import org.educa.core.bean.DiplomaBean;
import org.educa.core.entities.model.SesionUsuario;

public class AdaptadorDiploma {

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private SesionUsuario sesionUsuario;

	public AdaptadorDiploma(SesionUsuario sesionUsuario) {
		this.sesionUsuario = sesionUsuario;
	}

	public DiplomaBean adaptar() {
		DiplomaBean resultado = new DiplomaBean();
		resultado.setNombreCurso(sesionUsuario.getCurso().getNombre());
		resultado.setFechaInicio(sdf.format(sesionUsuario.getSesion().getFechaDesde()));
		return resultado;
	}

}
