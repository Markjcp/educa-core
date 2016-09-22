package org.educa.core.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.educa.core.dao.ParametroRepository;
import org.educa.core.exceptions.MailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

public class ClienteEmail {

	private static final String TEXT_HTML_CHARSET_UTF_8 = "text/html; charset=utf-8";

	private static final String MAIL_SMTP_PORT = "mail.smtp.port";

	private static final String MAIL_SMTP_HOST = "mail.smtp.host";

	private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";

	private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";

	@Autowired
	@Qualifier("parametroRepository")
	private ParametroRepository parametroRepository;

	private Session session;

	public ClienteEmail(final ClienteEmailConfigurador configurador) {
		this.session = Session.getInstance(configurador.getProps(), new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(configurador.getUsuario(), configurador.getClave());
			}
		});
	}

	public void enviarMail(String to, String from, String asunto, String html) {
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(asunto);
			message.setContent(html, TEXT_HTML_CHARSET_UTF_8);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new MailException("Error al enviar email");
		}
	}

}
