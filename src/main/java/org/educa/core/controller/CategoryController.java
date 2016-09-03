package org.educa.core.controller;

import java.util.List;

import org.educa.core.dao.CategoryRepository;
import org.educa.core.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CategoryController {
	
	@Autowired
	@Qualifier("categoryRepository")
	private CategoryRepository categoryRepository;
	
    @RequestMapping("/categories")
    public String categories(Model model){
    	List<Category> categories = (List<Category>)categoryRepository.findAll(); 
    	int categorySize = categories.size();
    	model.addAttribute("categorySize", categorySize);
    	return "category";
    }


}
