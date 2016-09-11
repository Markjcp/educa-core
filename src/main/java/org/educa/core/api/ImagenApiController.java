package org.educa.core.api;

import org.educa.core.dao.CursoRepository;
import org.educa.core.entities.constants.ConstantesDelModelo;
import org.educa.core.entities.model.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/img")
public class ImagenApiController {
	
	@Autowired
	@Qualifier("cursoRepository")
	private CursoRepository cursoRepository;

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "{nombreImagen}", headers="Accept=*/*",produces = {"image/png","image/jpg"})
	public byte[] listar(@PathVariable String nombreImagen) {
		Long cursoId = obtenerIdDelNombre(nombreImagen);
		Curso curso = cursoRepository.findOne(cursoId);
		return curso.getImagen();
	}

	private Long obtenerIdDelNombre(String nombreImagen) {
		String fileSplitted[] = nombreImagen.split("/");
		String imageName = fileSplitted[fileSplitted.length-1];
		String cursoId = imageName.split(ConstantesDelModelo.SEPARADOR_DE_IMAGEN)[0];
		return Long.valueOf(cursoId);
	}


}
