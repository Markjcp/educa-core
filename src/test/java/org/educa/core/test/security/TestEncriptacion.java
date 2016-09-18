package org.educa.core.test.security;

import static org.junit.Assert.*;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.junit.Test;

public class TestEncriptacion {
	
	String passwordCorrecto= "password";
	
	String passwordIncorrecto= "password1";
	
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

}
