package org.educa.core.test.security;

import java.io.IOException;

import org.educa.core.entities.model.ExamenUnidad;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestSerializacion {

	
	@Test
	public void serializarExamen() throws JsonParseException, JsonMappingException, IOException{
		String json = "{\"id\":{\"numero\":1,\"idCurso\":1,\"idExamen\":1},\"cantPreguntasUsuario\":2,\"preguntas\":[{\"id\":{\"numero\":1,\"idCurso\":1,\"idExamen\":1,\"idPregunta\":1},\"enunciado\":\"Que te parece la selección?\",\"multipleChoice\":false,\"respuesta\":\"Una cagada\",\"opciones\":[{\"id\":{\"numero\":1,\"idCurso\":1,\"idExamen\":1,\"idPregunta\":1,\"idOpcion\":1},\"texto\":\"Uno\",\"esCorrecta\":true},{\"id\":{\"numero\":1,\"idCurso\":1,\"idExamen\":1,\"idPregunta\":1,\"idOpcion\":2},\"texto\":\"Dos\",\"esCorrecta\":false},{\"id\":{\"numero\":1,\"idCurso\":1,\"idExamen\":1,\"idPregunta\":1,\"idOpcion\":3},\"texto\":\"Tres\",\"esCorrecta\":false},{\"id\":{\"numero\":1,\"idCurso\":1,\"idExamen\":1,\"idPregunta\":1,\"idOpcion\":4},\"texto\":\"Cuatro\",\"esCorrecta\":false}]},{\"id\":{\"numero\":1,\"idCurso\":1,\"idExamen\":1,\"idPregunta\":2},\"enunciado\":\"messi o maradona\",\"multipleChoice\":false,\"respuesta\":\"diegote,messi\",\"opciones\":[]}]}";
		ObjectMapper mapper = new ObjectMapper();
		ExamenUnidad examen = mapper.readValue(json,ExamenUnidad.class);
		System.out.println(examen);
	}
}
