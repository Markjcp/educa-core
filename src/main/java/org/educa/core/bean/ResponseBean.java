package org.educa.core.bean;

public class ResponseBean {

	private boolean ok;
	
	private Integer codigoError;
	
	private String descripcionError; 
	
	public ResponseBean() {
		super();
	}

	public ResponseBean(boolean ok) {
		super();
		this.ok = ok;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public Integer getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(Integer codigoError) {
		this.codigoError = codigoError;
	}

	public String getDescripcionError() {
		return descripcionError;
	}

	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}
	
	
}
