package org.educa.core.controller;

import org.educa.core.controller.forms.PruebaForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/pruebaHtml")
@SessionAttributes({ "pruebaForm" })
public class PruebaHtmlController {
	
	private static final String PRUEBA_HTML = "views/pruebas/prueba-html";

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {		
		model.addAttribute("pruebaForm", new PruebaForm());
		//model.addAttribute("videoCargado", true);		
		
		return PRUEBA_HTML;
	}
}
