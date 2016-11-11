package org.educa.core.controller;

import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

import org.educa.core.bean.ContadorEstadistica;
import org.educa.core.controller.forms.EstadisticaForm;
import org.educa.core.entities.model.Categoria;
import org.educa.core.exceptions.SerializacionException;
import org.educa.core.services.EstadisticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("estadisticas")
public class EstadisticasController {
	
	private Logger logger = Logger.getLogger(EstadisticasController.class.toString());
	
	@Autowired
	private EstadisticaService estadisticaService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(Model model) {

		
		EstadisticaForm estadisticaForm = new EstadisticaForm();
		estadisticaForm.setChartUrl("");
		estadisticaForm.setChartContentData(generarJsonDeDatos(null, null, null)); //@TODO sacar el hardcodeado y cambar el nombre
		

		model.addAttribute("estadisticaForm", estadisticaForm);

		return "views/estadistica/home-estadistica";
	}
	
	private String generarJsonDeDatos(Date desde, Date hasta, Long usuarioId){
		try {
			Map<Categoria,ContadorEstadistica> datos = estadisticaService.buscarEstadisticas(desde, hasta, usuarioId);
			return estadisticaService.adaptarResultados(datos);			
		} catch (SerializacionException e) {
			logger.severe("Error deserializando: " + e);
			return "";
		}
	}

}
