package org.educa.core.validator;

import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.educa.core.controller.forms.UnidadForm;
import org.educa.core.entities.constants.ConstantesDelModelo;
import org.educa.core.entities.model.MaterialUnidad;
import org.educa.core.entities.model.Unidad;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.stereotype.Component;

@Component
public class MaterialTeoricoValidator {
	
	private static final Logger logger = Logger.getLogger(MaterialTeoricoValidator.class.toString());
	
	public boolean intentaLimpiarContenido(String materialTeorico, Unidad unidad) {		
		if(unidad.getMaterial() != null && !unidad.getMaterial().isEmpty()){
			MaterialUnidad materialUnidad = unidad.getMaterial().get(0);			
			if(materialUnidad.getMaterial() != null && materialUnidad.getMaterial().length > 0){
				boolean contenidoVacio = estaContenidoVacio(materialTeorico);
				//Contiene material y lo intenta limpiar
				if(contenidoVacio){
					return true;
				}				
			}
		}
		
		return false;
	}
	
	@Deprecated
	public boolean estaContenidoVacio(String contenido){
		SAXBuilder saxBuilder = new SAXBuilder();
		try {
			Document doc = saxBuilder.build(new StringReader(contenido));
		    String contenidoPuro = doc.getRootElement().getText();
		    contenidoPuro = contenidoPuro.trim();
		    System.out.println(contenidoPuro);
		    
		    if(contenidoPuro == null || contenidoPuro.isEmpty()){
		    	return true;
		    }
		} catch (JDOMException e) {			
		    // handle JDOMException
			System.out.println(e);
			logger.log(Level.WARNING, e.getMessage());
		} catch (IOException e) {			
		    // handle IOException
			System.out.println(e);
			logger.log(Level.WARNING, e.getMessage());
		}
		
		return false;
	}

	public String validaContenidoMaterialUnidad(UnidadForm unidadForm) {
		String mensaje = null;
		String materialTeorico = unidadForm.getMaterialTeorico();
		if(materialTeorico == null || materialTeorico.isEmpty()){
			mensaje = "Debe de cargar material teórico para poder guardarlo.";
		} else {
			byte[] materialBytes = unidadForm.getMaterialTeorico().getBytes();
			if(materialBytes.length > ConstantesDelModelo.MAX_TAM_MATERIAL_TEORICO){
				mensaje = "El tamaño del material teorico debe ser menor a " + ConstantesDelModelo.MAX_TAM_MATERIAL_TEORICO + " " + ConstantesDelModelo.UNIDAD_TAM_MATERIAL_TEORICO;
			}
		}
		
		return mensaje;
	}
}
