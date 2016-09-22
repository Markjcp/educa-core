package org.educa.core.util;

import java.util.Properties;

public class ClienteEmailConfiguradorSimple implements ClienteEmailConfigurador{

	private Properties props;

	private String usuario;

	private String clave;

	public ClienteEmailConfiguradorSimple(Properties props, String usuario, String clave) {
		super();
		this.props = props;
		this.usuario = usuario;
		this.clave = clave;
	}

	@Override
	public Properties getProps() {
		return props;
	}

	@Override
	public String getUsuario() {
		return usuario;
	}

	@Override
	public String getClave() {
		return clave;
	}

}
