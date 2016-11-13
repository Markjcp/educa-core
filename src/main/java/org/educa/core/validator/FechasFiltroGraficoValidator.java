package org.educa.core.validator;

import org.educa.core.controller.forms.EstadisticaForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class FechasFiltroGraficoValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return EstadisticaForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		EstadisticaForm form = (EstadisticaForm) target;
		if(form.getFechaDesde()!=null && form.getFechaHasta()!=null && form.getFechaDesde().after(form.getFechaHasta())){
			errors.rejectValue("fechaDesde", null, "La fecha desde no puede ser mayor a la fecha hasta");
		}
		
	}

}
