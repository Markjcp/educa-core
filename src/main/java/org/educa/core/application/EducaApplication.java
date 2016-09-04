package org.educa.core.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "org.educa.core" })
@EntityScan(basePackages={"org.educa.core.entities"})
@EnableJpaRepositories(basePackages={"org.educa.core.dao"})
//@ComponentScan(basePackages={"org.educa.core.config","org.educa.core.services","org.educa.core.api","org.educa.core.controller"})
public class EducaApplication {
	public static void main(String[] args) {
		SpringApplication.run(EducaApplication.class, args);
	}
}
