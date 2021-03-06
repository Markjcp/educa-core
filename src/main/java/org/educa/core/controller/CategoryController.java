package org.educa.core.controller;

import java.util.List;

import org.educa.core.dao.CategoriaRepository;
import org.educa.core.entities.model.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CategoryController {
	
	@Autowired
	@Qualifier("categoriaRepository")
	private CategoriaRepository categoriaRepository;
	
    @RequestMapping("/categories")
    public String categories(Model model){
    	List<Categoria> categories = (List<Categoria>)categoriaRepository.findAll(); 
    	int categorySize = categories.size();
    	model.addAttribute("categorySize", categorySize);
    	return "category";
    }


}
