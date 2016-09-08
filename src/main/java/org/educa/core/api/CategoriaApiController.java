package org.educa.core.api;

import java.util.List;

import org.educa.core.dao.CategoriaRepository;
import org.educa.core.entities.model.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaApiController {
	
	@Autowired
	@Qualifier("categoriaRepository")
	private CategoriaRepository categoriaRepository;

    @RequestMapping(method=RequestMethod.GET,value="listar")
    public List<Categoria> categories(){
    	List<Categoria> categories = (List<Categoria>)categoriaRepository.findAll(); 
    	return categories;
    }

}
