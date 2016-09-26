package org.educa.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    UserDetailsService autenticacionServiceImpl;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/login", "/registro", "/activar-cuenta").permitAll().
		antMatchers("/api/**").permitAll().
		antMatchers("/cursoAdmin/**").hasAuthority("ROL_ADMIN").
		antMatchers("/cursoNoAdmin/**").hasAuthority("ROL_DOC").
		antMatchers("/docente/**").hasAuthority("ROL_DOC").
		anyRequest().authenticated()
        .and().exceptionHandling().accessDeniedPage("/login")
		.and().logout().permitAll();
	}
}
