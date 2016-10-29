package org.educa.core.api;

import org.educa.core.dao.SesionRepository;
import org.educa.core.entities.model.ComponenteId;
import org.educa.core.entities.model.Foro;
import org.educa.core.entities.model.Sesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/foro")
public class ForoApiController {

	@Autowired
	private SesionRepository sesionRepository;

	@RequestMapping(method = RequestMethod.GET, value = "{idSesion}/{idCurso}")
	public ResponseEntity<Foro> foroPorIdSesion(@PathVariable Integer idSesion, @PathVariable Long idCurso) {
		ComponenteId idSesionComponenteId = new ComponenteId();
		idSesionComponenteId.setIdCurso(idCurso);
		idSesionComponenteId.setNumero(idSesion);
		Sesion sesion = sesionRepository.findOne(idSesionComponenteId);
		if (sesion == null || sesion.getForo() == null) {
			return new ResponseEntity<Foro>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Foro>(sesion.getForo(), HttpStatus.OK);
	}

}
