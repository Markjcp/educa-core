package org.educa.core.services.impl;

import org.educa.core.dao.EmailTemplateRepository;
import org.educa.core.dao.ParametroRepository;
import org.educa.core.entities.model.Docente;
import org.educa.core.entities.model.EmailTemplate;
import org.educa.core.entities.model.Usuario;
import org.educa.core.exceptions.MailException;
import org.educa.core.exceptions.ServiceException;
import org.educa.core.services.NotificacionService;
import org.educa.core.util.ClienteConfiguradorParametro;
import org.educa.core.util.ClienteEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.stringtemplate.v4.ST;

@Service
@Transactional(propagation = Propagation.REQUIRED, noRollbackFor=ServiceException.class)
public class NotificacionServiceImpl implements NotificacionService {
	
	@Autowired
	private ClienteConfiguradorParametro clienteConfiguradorParametro;
	
	@Autowired
	private ParametroRepository parametroRepository;
	
	@Autowired
	private EmailTemplateRepository emailTemplateRepository;
	

	@Override
	public void notificarCursoNuevo(Docente profesor, String codigoCurso, String cursoNombre) {
		// TODO [ediaz] hay que mandar el mail al profe de que tiene asignado un nuevo curso

	}

	@Override
	public void enviarActivacion(Usuario usuario) {
		ClienteEmail cliente = new ClienteEmail(clienteConfiguradorParametro);
		String from = null;
		String asunto = null;
		try {
			from = parametroRepository.findOne("MAIL_FROM").getValor();
			asunto = parametroRepository.findOne("MAIL_REGISTRACION_ASUNTO").getValor();			
			EmailTemplate template = emailTemplateRepository.findOne("EMAIL_ACTIVACION_USUARIO");
			ST st = new ST(template.getValor(),'*','*');
			st.add("clave", usuario.getClaveActivacion());
			st.add("idusuario", usuario.getId());
			String emailBody = st.render();
			cliente.enviarMail(usuario.getEmail(), from, asunto, emailBody);
		} catch (Exception e) {
			throw new MailException("No se pudo obtener los parametros para enviar el mail");
		}
		
	}

}
