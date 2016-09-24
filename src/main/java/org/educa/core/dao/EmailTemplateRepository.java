package org.educa.core.dao;

import org.educa.core.entities.model.EmailTemplate;
import org.springframework.data.repository.CrudRepository;

public interface EmailTemplateRepository extends CrudRepository<EmailTemplate, String> {

}
