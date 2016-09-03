package org.educa.core.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.educa.core.dao.CategoryRepository;
import org.educa.core.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Path("/api/category")
@Deprecated
public class CategoryResource {
	
	@Autowired
	@Qualifier("categoryRepository")
	private CategoryRepository categoryRepository;
	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Category> categories() {
		return (List<Category>) categoryRepository.findAll();
	}

}
