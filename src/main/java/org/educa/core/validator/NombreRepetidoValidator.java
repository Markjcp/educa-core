package org.educa.core.validator;

import java.util.List;

import org.educa.core.dao.CursoRepository;
import org.educa.core.entities.model.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class NombreRepetidoValidator implements Validator {

	@Autowired
	@Qualifier(value = "cursoRepository")
	private CursoRepository cursoRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return Curso.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Curso curso = (Curso) target;
		List<Curso> cursos = cursoRepository.findByNombre(curso.getNombre().trim());
		if (cursos != null && !cursos.isEmpty()) {
			errors.rejectValue("curso.nombre", null, "Ya existe otro curso con ese nombre");
		}
	}

}
