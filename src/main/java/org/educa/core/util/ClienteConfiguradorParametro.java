package org.educa.core.util;

import java.util.Properties;

import org.educa.core.dao.ParametroRepository;
import org.educa.core.exceptions.MailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn("parametroRepository")
public class ClienteConfiguradorParametro implements ClienteEmailConfigurador {

	@Autowired
	private ParametroRepository parametroRepository;

	private Properties props;

	private String usuario;

	private String clave;

	private void init() {
		this.props = new Properties();
		props.put("mail.smtp.auth", parametroRepository.findOne("mail.smtp.auth").getValor());
		props.put("mail.smtp.starttls.enable", parametroRepository.findOne("mail.smtp.starttls.enable").getValor());
		props.put("mail.smtp.host", parametroRepository.findOne("mail.smtp.host").getValor());
		props.put("mail.smtp.port", parametroRepository.findOne("mail.smtp.port").getValor());
		usuario = parametroRepository.findOne("mail.smtp.user").getValor();
		clave = parametroRepository.findOne("mail.smtp.password").getValor();
		if (props == null || props.isEmpty() || usuario == null || usuario.isEmpty() || clave == null
				|| clave.isEmpty()) {
			throw new MailException("No se pudo obtener los paremetros SMTP");
		}
	}

	@Override
	public Properties getProps() {
		if (props == null) {
			init();
		}
		return props;
	}

	@Override
	public String getUsuario() {
		if (usuario == null) {
			init();
		}
		return usuario;
	}

	@Override
	public String getClave() {
		if (clave == null) {
			init();
		}
		return clave;
	}

}
