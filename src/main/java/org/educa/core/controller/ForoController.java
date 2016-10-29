package org.educa.core.controller;

import java.util.Calendar;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.validation.Valid;

import org.educa.core.controller.forms.ForoForm;
import org.educa.core.dao.SesionRepository;
import org.educa.core.entities.model.Curso;
import org.educa.core.entities.model.EstadoForo;
import org.educa.core.entities.model.EstadoPublicacion;
import org.educa.core.entities.model.Foro;
import org.educa.core.entities.model.Sesion;
import org.educa.core.entities.model.Tema;
import org.educa.core.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("foro")
public class ForoController {
	
	private static final String LISTADO_SESION_FORO = "views/curso/foro/listado-sesiones-foro";	
	
	@Autowired
	private CursoService cursoService;
	
	@Autowired
	@Qualifier("sesionRepository")
	private SesionRepository sesionRepository;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String index(@PathVariable("id") long id, Model model) {
		Curso curso = this.cursoService.encontrarCursoPorId(id);
		ForoForm foroForm = new ForoForm();
		foroForm.setCurso(curso);
		
		SortedSet<Sesion> sesiones = sesionRepository.findByFechaAndIdCurso(Calendar.getInstance().getTime(), id);
		//A cada sesion le creo un foro si viene en null, idem para sus componentes. Esto se hace para evitar nulls en la vista
		SortedSet<Sesion> sesionesOk = new TreeSet<Sesion>(); 
		for(Sesion sesion : sesiones){
			//Foro null
			if(sesion.getForo() == null){
				sesion.setForo(new Foro());
				//TODO BORRARLO ES SOLO DE PRUEBA LA SIG LINEA
				Foro foro = sesion.getForo();
				foro.setEstado(EstadoForo.MODERADO);
				sesion.setForo(foro);
				//FIN DE BORRAR
			}
			
			//Temas null
			if(sesion.getForo() != null && sesion.getForo().getTemas() == null){
				Foro foro = sesion.getForo();
				//TODO ESTO COMENTADO SERIA LO IDEAL
				/*foro.setTemas(new TreeSet<Tema>());
				sesion.setForo(foro);*/
				
				//TODO EDIAZ NO OLVIDAR QUE LOS TEMAS Y LOS COMENTARIOS QUE ESTAN OCULTOS, SE DEBEN DE MOSTRAR DE ALGUNA MANERA!!! O QUE NO SE MUESTREN - HAY QUE VALIDARLO!
				
				//TODO LO Q SIGUE BORRARLO DESPUES, ES SOLO DE PRUEBA
				SortedSet<Tema> temas = new TreeSet<Tema>();
				Tema tema = new Tema();
				tema.setTitulo("Tema de prueba");
				temas.add(tema);
				Tema tema2 = new Tema();
				tema2.setTitulo("Tema de prueba");
				tema2.setEstado(EstadoPublicacion.APROBADO);
				temas.add(tema2);
				foro.setTemas(temas);
				sesion.setForo(foro);
			}
			
			sesionesOk.add(sesion);
		}
		
		model.addAttribute("foroForm", foroForm);
		model.addAttribute("sesiones", sesionesOk);
		
		
		return LISTADO_SESION_FORO;
	}
	
	@RequestMapping(value = "/altaTema/{idCurso}/{nroSesion}", method = RequestMethod.GET)
	public String altaTema(@PathVariable("idCurso") long idCurso, @PathVariable("nroSesion") int nroSesion, @ModelAttribute @Valid ForoForm foroForm, BindingResult bindingResult, Model model) {
		//TODO
		System.out.println("ENTRO A DAR DE ALTA UN TEMA");
		
		return LISTADO_SESION_FORO;
	}
	
	@RequestMapping(value = "/detalleForo/{idCurso}/{nroSesion}", method = RequestMethod.GET)
	public String detalleForo(@PathVariable("idCurso") long idCurso, @PathVariable("nroSesion") int nroSesion, Model model) {
		//TODO HAY Q HACERLO!
		Curso curso = this.cursoService.encontrarCursoPorId(idCurso);
		ForoForm foroForm = new ForoForm();
		foroForm.setCurso(curso);
		
		SortedSet<Sesion> sesiones = sesionRepository.findByFechaAndIdCurso(Calendar.getInstance().getTime(), idCurso);
		
		model.addAttribute("foroForm", foroForm);
		model.addAttribute("sesiones", sesiones);
		
		
		return LISTADO_SESION_FORO;
	}	
}
