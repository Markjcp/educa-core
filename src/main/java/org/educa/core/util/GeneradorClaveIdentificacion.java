package org.educa.core.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public class GeneradorClaveIdentificacion {
	
	private SecureRandom random = new SecureRandom();

	  public String proximoId() {
	    return new BigInteger(130, random).toString(32);
	  }

}
