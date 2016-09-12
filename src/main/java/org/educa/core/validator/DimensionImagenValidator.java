package org.educa.core.validator;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.educa.core.entities.constants.ConstantesDelModelo;
import org.educa.core.entities.model.Curso;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DimensionImagenValidator implements Validator {

	private static final Logger logger = Logger.getLogger(DimensionImagenValidator.class.toString());
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Curso.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Curso curso = (Curso) target;
		BufferedImage image;
		try {
			image = ImageIO.read(curso.getFoto().getInputStream());
			Integer width = image.getWidth();
			Integer height = image.getHeight();
			if(!(width.equals(ConstantesDelModelo.ANCHO_1)&&height.equals(ConstantesDelModelo.ALTURA_1))
					&& !(width.equals(ConstantesDelModelo.ANCHO_2)&&height.equals(ConstantesDelModelo.ALTURA_2))){
				errors.rejectValue("curso.foto", null, "La imágen debe ser de " + ConstantesDelModelo.ANCHO_1 + "x" + ConstantesDelModelo.ALTURA_1 +
						" ó de " + ConstantesDelModelo.ANCHO_2 + "x" + ConstantesDelModelo.ALTURA_2);
			}
		} catch (IOException e) {
			logger.severe("Error validando la imágen " + e.getMessage());
			errors.rejectValue("curso.foto", null, "Error validando la imágen");
		}
	}

}
