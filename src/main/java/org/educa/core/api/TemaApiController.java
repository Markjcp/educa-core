package org.educa.core.api;

import java.util.ArrayList;
import java.util.List;

import org.educa.core.dao.TemaRepository;
import org.educa.core.entities.model.Tema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/tema")
public class TemaApiController {
	
	

	@Autowired
	private TemaRepository temaRepository;

	
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "listar/{idForo}")
	public ResponseEntity<List<Tema>> listar(@PathVariable Long idForo) {
		List<Tema> temas = new ArrayList<Tema>();
		
		try {
			temas = temaRepository.findByIdForo(idForo);
			return new ResponseEntity<List<Tema>>(temas, HttpStatus.OK);	
		} catch (Exception e) {
			return new ResponseEntity<List<Tema>>(temas, HttpStatus.NOT_FOUND);
		}
		
	}

	

}
