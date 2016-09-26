package org.educa.core.test.security;

import static org.junit.Assert.*;

import org.educa.core.entities.model.EmailTemplate;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.junit.Test;
import org.stringtemplate.v4.ST;

public class TestEncriptacion {
	
	String passwordCorrecto= "password";
	
	String passwordIncorrecto= "password1";
	
	String encriptadoCorrecto = "VIyIwyC5YYs0fVrl/Wsl4XdUgqpFgNVG";
	
	@Test
	public void testAutenticacionDebePasar(){
		BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
		String encryptedPassword = passwordEncryptor.encryptPassword(passwordCorrecto);
		System.out.println(encryptedPassword);
		assertEquals(passwordEncryptor.checkPassword(passwordCorrecto, encryptedPassword),true);
	}
	
	@Test
	public void testAutenticacionDebeFallar(){
		BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
		String encryptedPassword = passwordEncryptor.encryptPassword(passwordCorrecto);
		System.out.println(encryptedPassword);
		assertEquals(passwordEncryptor.checkPassword(passwordIncorrecto, encryptedPassword),false);		
	}
	
	@Test
	public void testDesinscripcionDebePasar(){
		BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
		assertEquals(passwordEncryptor.checkPassword(passwordCorrecto, encriptadoCorrecto),true);		
	}
	
	public static void main(String[] args) {
		String prueba = "<html style=\"margin-top: 20px; padding-top: 10px; padding-left: 10px; margin-left: 40px; border-width: 30px 100px 200px; border-style: solid; border-color: #f1ebf0; -moz-border-top-colors: none; -moz-border-right-colors: none; -moz-border-bottom-colors: none; -moz-border-left-colors: none; border-image: none; margin-right: 20px;\"><head> <link href=\"http://fonts.googleapis.com/css?family=Roboto\" rel=\"stylesheet\" type=\"text/css\"> </head> <body style=\"font-family: Roboto;\"> <h1 style=\"padding-left: 300px;\">Bienvenido a Educa</h1> <p style=\"color: #9090a2;\">Para poder comenzar a utilizar la plataforma, por favor active su cuenta accionando el siguiente bot&oacute;n</p> <div style=\"padding-left: 400px;\"> <a href=\"http://educa-mnforlenza.rhcloud.com/activar-cuente.html?clave-activacion=*clave*&id-usuario=*idusuario*\"> <button style=\"background-color: rgb(230, 81, 0) ! important; border: medium none; display: inline-block; height: 36px; line-height: 36px; outline: 0px none; padding: 0px 2rem; text-transform: uppercase; vertical-align: middle; color: white ! important;\">Activar cuenta </button> </a> </div> </body></html> ";		
		ST st = new ST(prueba,'*','*');
		st.add("clave", "123");
		st.add("idusuario", 1L);
		System.out.println(st.render());		
	}

}

