package org.educa.core.exceptions;

import org.springframework.core.NestedRuntimeException;

public class EducaDataAccessException extends NestedRuntimeException {

	private static final long serialVersionUID = 7157393642996131364L;

	public EducaDataAccessException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
