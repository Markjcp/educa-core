package org.educa.core.config;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("org.educa.core")
@EntityScan("org.educa.core.model")
public class DaoConfiguration {

}
