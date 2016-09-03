package org.educa.core.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"org.educa.core.config","org.educa.core.api","org.educa.core.controller"})
public class EducaApplication {
	public static void main(String[] args) {
		SpringApplication.run(EducaApplication.class, args);
	}
}
