package com.piter.videoapi.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.piter.videoapi.repository.AutenticacaoRepository;

@EnableWebSecurity
@Configurable // Vamos ter configurações nessa classe como configuração de beans
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
	
	// Inicialmente fica tudo bloqueado, temos que ir liberando
	
	// Temos 3 métodos "configure" para sobrescrever. Devemos definir os 3
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private AutenticacaoRepository autenticacaoRepository;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Configurações de AUTENTICAÇÃO
		
		//Falar ao SPRING qual a classe que tem a lógica de autenticação
		auth.userDetailsService(autenticacaoService)
			.passwordEncoder(new BCryptPasswordEncoder()) //MD5 e CHAR2 não são mais seguros hoje
		;
		
		// super.configure(auth);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Configurações de AUTORIZAÇÃO das URLs
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/videos").permitAll() // Qual URL quer filtrar e o que fazer
		.antMatchers(HttpMethod.GET, "/videos/*").permitAll()
		.antMatchers(HttpMethod.POST, "/auth").permitAll()
		.anyRequest().authenticated()
		.and().csrf().disable() // Por usar JWT não precisa dessa proteção a ataques CSRF
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		// Adicionamos o nosso filtro antes do filtro que o SpringBoot já tem para a autenticação
		.and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, autenticacaoRepository), UsernamePasswordAuthenticationFilter.class)
		;
		// Não chamamos mais super.configure e já estamos usando o anyRequest().authenticated
		//super.configure(http);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// Confirugações de RECURSOS ESTÁTICOS solicitações de imagens, css, js, ...
		web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configurations/**",
							"/swagger-resources/**");
		
		//super.configure(web);
	}
	
	// A classe que estamos herdando já tem um método que sabe criar o AuthenticationManager
	@Override  // Vamos implementar esse método
	@Bean  // Para ensinar ao SPRING que o método retorna um Bean (para aprender a Injetar a dependência)
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}

}
