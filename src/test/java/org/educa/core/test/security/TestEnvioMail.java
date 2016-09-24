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
		email.enviarMail("mnforlenza@gmail.com", "yo@yo.com", "A per", "<html style=\"margin-top: 20px; padding-top: 10px; padding-left: 10px; margin-left: 40px; border-width: 30px 100px 200px; border-style: solid; border-color: #f1ebf0; -moz-border-top-colors: none; -moz-border-right-colors: none; -moz-border-bottom-colors: none; -moz-border-left-colors: none; border-image: none; margin-right: 20px;\"><head> <link href=\"http://fonts.googleapis.com/css?family=Roboto\" rel=\"stylesheet\" type=\"text/css\"> </head> <body style=\"font-family: Roboto;\"> <h1 style=\"padding-left: 170px;\">Bienvenido a Educa</h1> <p style=\"color: #9090a2;\">Para poder comenzar a utilizar la plataforma, por favor active su cuenta accionando el siguiente bot&oacute;n</p> <div style=\"padding-left: 220px;\"> <a href=\"http://educa-mnforlenza.rhcloud.com/activar-cuente.html?clave-activacion=clave*&id-usuario=*idusuario*\"> <button style=\"background-color: rgb(230, 81, 0) ! important; border: medium none; display: inline-block; height: 36px; line-height: 36px; outline: 0px none; padding: 0px 2rem; text-transform: uppercase; vertical-align: middle; color: white ! important;\">Activar cuenta </button> </a> </div> </body></html> ");

	}

}
