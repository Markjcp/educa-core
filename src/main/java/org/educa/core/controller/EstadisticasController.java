package org.educa.core.controller;

import org.educa.core.controller.forms.EstadisticaForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("estadisticas")
public class EstadisticasController {

	@RequestMapping(value = "/{idUsuario}", method = RequestMethod.GET)
	public String index(@PathVariable("idUsuario") long id, Model model) {

		
		EstadisticaForm estadisticaForm = new EstadisticaForm();
		estadisticaForm.setChartUrl("");
		

		model.addAttribute("estadisticaForm", estadisticaForm);

		return "views/estadistica/home-estadistica";
	}

}
