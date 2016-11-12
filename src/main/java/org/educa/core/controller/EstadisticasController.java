package org.educa.core.controller;

import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.educa.core.api.AbstractController;
import org.educa.core.bean.ContadorEstadistica;
import org.educa.core.controller.forms.EstadisticaForm;
import org.educa.core.entities.model.Categoria;
import org.educa.core.entities.model.Usuario;
import org.educa.core.exceptions.SerializacionException;
import org.educa.core.exceptions.SinResultadosException;
import org.educa.core.services.EstadisticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("estadisticas")
public class EstadisticasController extends AbstractController {
	
	private Logger logger = Logger.getLogger(EstadisticasController.class.toString());
	
	private static final String INDEX = "views/estadistica/home-estadistica";

	private static final Long ROL_ADMIN_USUARIO_ID = 1L;
	
	@Autowired
	private EstadisticaService estadisticaService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request) {

		
		EstadisticaForm estadisticaForm = (EstadisticaForm) model.asMap().get("estadisticaForm");
		
		if(estadisticaForm == null){
			estadisticaForm = new EstadisticaForm();
			estadisticaForm.setChartUrl("");
			estadisticaForm.setChartContentData(generarJsonDeDatos(null, null, obtenerUsuarioId(request))); //@TODO sacar el hardcodeado y cambar el nombre			
		}
		model.addAttribute("estadisticaForm", estadisticaForm);

		return INDEX;
	}
	
	@RequestMapping(value="/buscar", method = RequestMethod.POST)
	public String buscar(@ModelAttribute @Valid EstadisticaForm estadisticaForm, BindingResult bindingResult, 
			Model model, HttpServletRequest request){		
		estadisticaForm.setChartContentData(generarJsonDeDatos(estadisticaForm.getFechaDesde(), estadisticaForm.getFechaHasta(), obtenerUsuarioId(request)));
		model.addAttribute("estadisticaForm", estadisticaForm);
		return INDEX;
	}
	
	private String generarJsonDeDatos(Date desde, Date hasta, Long usuarioId){
		try {
			Map<Categoria,ContadorEstadistica> datos = estadisticaService.buscarEstadisticas(desde, hasta, usuarioId);
			return estadisticaService.adaptarResultados(datos);			
		} catch (SerializacionException e) {
			logger.severe("Error deserializando: " + e);
			return "";
		} catch (SinResultadosException e){
			logger.info("Sin resultados");
			return "";			
		}
	}
	
	private Long obtenerUsuarioId(HttpServletRequest request){
		Usuario usuario = obtenerUsuarioLogueado(request);
		if(usuario.getRol().getId().equals(ROL_ADMIN_USUARIO_ID)){
			return null;
		}
		return usuario.getId();
	}

}
