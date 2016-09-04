package org.educa.core.api;

import java.util.List;

import org.educa.core.dao.CategoryRepository;
import org.educa.core.entities.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryApiController {
	
	@Autowired
	@Qualifier("categoryRepository")
	private CategoryRepository categoryRepository;
	
    @RequestMapping(method=RequestMethod.GET,value="all")
    public List<Category> categories(){
    	List<Category> categories = (List<Category>)categoryRepository.findAll(); 
    	return categories;
    }

}
