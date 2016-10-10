package org.educa.core.controller;

import javax.validation.Valid;

import org.educa.core.controller.forms.PruebaForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/pruebaVideo")
@SessionAttributes({ "pruebaForm" })
public class PruebaVideoController {

	private static final String PRUEBA_VIDEO = "views/pruebas/prueba-video";

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {		
		model.addAttribute("pruebaForm", new PruebaForm());
		model.addAttribute("mostrar", false);		
		
		return PRUEBA_VIDEO;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String guardar(@ModelAttribute @Valid PruebaForm pruebaForm, BindingResult bindingResult, Model model) {		
		model.addAttribute("pruebaForm", pruebaForm);
		model.addAttribute("mostrar", true);
		
		return PRUEBA_VIDEO;
	}
}
