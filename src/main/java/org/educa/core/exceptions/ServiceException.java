package org.educa.core.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private List<String> errores;
	private String error;

	public ServiceException(String message) {
		super(message);
		error = message;
	}

	public ServiceException(String message, RuntimeException innerException) {
		super(message, innerException);
		error = message;
	}

	public ServiceException(String message, Exception innerException) {
		super(message, innerException);
		error = message;
	}
	
	public ServiceException(String message, List<String> errores) {
		super(message + " - " + errores.toString());
		error = message;
		this.errores = errores;
	}

	public List<String> getErrores() {
		if(errores == null)
			return new ArrayList<String>();
		return errores;
	}

	public String getError() {
		return error;
	}

}
