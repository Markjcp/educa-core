package org.educa.core.api;

import java.util.ArrayList;
import java.util.List;

import org.educa.core.dao.ComentarioRepository;
import org.educa.core.entities.model.Comentario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comentario")
public class ComentarioApiController {
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = "listar/{idTema}")
	public ResponseEntity<List<Comentario>> comentariosPorTema(@PathVariable Long idTema){
		List<Comentario> resultado = new ArrayList<Comentario>();
		try {
			resultado = comentarioRepository.findByIdTema(idTema);
			return new ResponseEntity<List<Comentario>>(resultado,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Comentario>>(resultado,HttpStatus.NOT_FOUND);
		}		
	}

}
