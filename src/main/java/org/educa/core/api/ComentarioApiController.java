package org.educa.core.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.educa.core.dao.ComentarioRepository;
import org.educa.core.dao.ForoRepository;
import org.educa.core.dao.TemaRepository;
import org.educa.core.entities.model.Comentario;
import org.educa.core.entities.model.EstadoForo;
import org.educa.core.entities.model.EstadoPublicacion;
import org.educa.core.entities.model.Foro;
import org.educa.core.entities.model.Tema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comentario")
public class ComentarioApiController {

	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Autowired
	private ForoRepository foroRepository;
	
	@Autowired
	private TemaRepository temaRepository;

	@RequestMapping(method = RequestMethod.GET, value = "listar/{idTema}")
	public ResponseEntity<List<Comentario>> comentariosPorTema(@PathVariable Long idTema) {
		List<Comentario> resultado = new ArrayList<Comentario>();
		try {
			resultado = comentarioRepository.findByIdTema(idTema);
			return new ResponseEntity<List<Comentario>>(resultado, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Comentario>>(resultado, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "crear")
	public ResponseEntity<Comentario> crear(@RequestBody Comentario comentario) {
		Comentario resultado = null;
		try {
			Tema tema = temaRepository.findOne(comentario.getIdTema());
			Foro foro = foroRepository.findOne(tema.getIdForo());
			Date fechaCreacion = Calendar.getInstance().getTime();
			comentario.setFechaCreacion(fechaCreacion);
			if(foro.getEstado().equals(EstadoForo.MODERADO)){
				comentario.setEstado(EstadoPublicacion.NO_APROBADO);				
				tema.setCantidadComentariosPorAprobar(tema.getCantidadComentariosPorAprobar()+1);
				temaRepository.save(tema);
				foro.setCantidadComentariosPorAprobar(foro.getCantidadComentariosPorAprobar()+1);
				foroRepository.save(foro);
			}else{
				comentario.setEstado(EstadoPublicacion.APROBADO);
				foro.setCantidadComentariosAprobados(foro.getCantidadComentariosAprobados()+1);
			}
			resultado = comentarioRepository.save(comentario);
			return new ResponseEntity<Comentario>(resultado, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Comentario>(resultado, HttpStatus.NOT_FOUND);

		}
	}

}
