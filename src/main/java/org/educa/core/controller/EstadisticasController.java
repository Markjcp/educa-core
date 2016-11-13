package org.educa.core.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.educa.core.api.AbstractController;
import org.educa.core.bean.ContadorEstadistica;
import org.educa.core.bean.ResultadosEstadisticas;
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
			Map<Categoria,ContadorEstadistica> datos = estadisticaService.buscarEstadisticas(null, null, obtenerUsuarioId(request));
			estadisticaForm.setResultados(convertirDatosEnLista(datos));
			estadisticaForm.setChartContentData(generarJsonDeDatos(datos)); 			
		}
		model.addAttribute("estadisticaForm", estadisticaForm);

		return INDEX;
	}
	
	private List<ResultadosEstadisticas> convertirDatosEnLista(Map<Categoria, ContadorEstadistica> datos) {
		List<ResultadosEstadisticas> resultados = new ArrayList<ResultadosEstadisticas>();
		if(datos != null && !datos.isEmpty()){
			for (Map.Entry<Categoria,ContadorEstadistica> entrada : datos.entrySet()) {
				resultados.add(new ResultadosEstadisticas(entrada.getKey(), entrada.getValue()));			
			}				
		}
		return resultados;
	}

	@RequestMapping(value="/buscar", method = RequestMethod.POST)
	public String buscar(@ModelAttribute @Valid EstadisticaForm estadisticaForm, BindingResult bindingResult, 
			Model model, HttpServletRequest request){
		Map<Categoria,ContadorEstadistica> datos = estadisticaService.buscarEstadisticas(estadisticaForm.getFechaDesde(), estadisticaForm.getFechaHasta(), obtenerUsuarioId(request));
		estadisticaForm.setChartContentData(generarJsonDeDatos(datos));
		estadisticaForm.setResultados(convertirDatosEnLista(datos));
		model.addAttribute("estadisticaForm", estadisticaForm);
		return INDEX;
	}
	
	private String generarJsonDeDatos(Map<Categoria,ContadorEstadistica> datos){
		try {
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
