package com.piter.videoapi.config.security;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configurable // Vamos ter configurações nessa classe como configuração de beans
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
	
	// Inicialmente fica tudo bloqueado, temos que ir liberando
	
	// Temos 3 métodos "configure" para sobrescrever. Devemos definir os 3
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Configurações de AUTENTICAÇÃO
		super.configure(auth);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Configurações de AUTORIZAÇÃO das URLs
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/videos").permitAll() // Qual URL quer filtrar e o que fazer
		.antMatchers(HttpMethod.GET, "/videos/*").permitAll()
		.anyRequest().authenticated() // QUalquer outra deve estar autenticado
		;
		super.configure(http);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// Confirugações de RECURSOS ESTÁTICOS solicitações de imagens, css, js, ...
		super.configure(web);
	}

}
