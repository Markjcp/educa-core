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
		email.enviarMail("mnforlenza@gmail.com", "yo@yo.com", "A per (oyto)", "<html ><head> <link href=\"http://fonts.googleapis.com/css?family=Roboto\" rel=\"stylesheet\" type=\"text/css\"> </head> <body style=\"font-family: Roboto;\"> <h1 style=\"text-align: center\">Bienvenido a Educa</h1> <p style=\"color: #9090a2; text-align: center\">Para poder comenzar a utilizar la plataforma, por favor active su cuenta accionando el siguiente botón</p> <div style=\"text-align: center\"> <a href=\"http://educa-mnforlenza.rhcloud.com/activar-cuenta?clave-activacion=*clave*&amp;id-usuario=*idusuario*\"> <button style=\"background-color: rgb(230, 81, 0) ! important; border: medium none; display: inline-block; height: 36px; line-height: 36px; outline: 0px none; padding: 0px 2rem; text-transform: uppercase; vertical-align: middle; color: white ! important;\">Activar cuenta </button> </a> </div> </body></html>");

	}

}
