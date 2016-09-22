package org.educa.core.test.security;

import java.util.Properties;

import org.educa.core.util.ClienteEmail;
import org.educa.core.util.ClienteEmailConfigurador;
import org.educa.core.util.ClienteEmailConfiguradorSimple;
import org.junit.Test;

public class TestEnvioMail {

	private String usuario = "educa.notificaciones@gmail.com";

	private String clave = "educaeduca";

	@Test
	public void enviarMailSimple() {

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		ClienteEmailConfigurador config = new ClienteEmailConfiguradorSimple(props, usuario, clave);

		ClienteEmail email = new ClienteEmail(config);
		email.enviarMail("mnforlenza@gmail.com", "yo@yo.com", "A per", "<html>A dormir</html>");

	}

}
