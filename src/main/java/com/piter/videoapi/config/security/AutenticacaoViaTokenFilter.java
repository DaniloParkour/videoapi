package com.piter.videoapi.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

												//Para chamad apenas uma vez a cada requisição
public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {
	
	// Não conseguimos injetar a dependencia aqui por AUTOWIRED
	private TokenService tokenService;
	
	public AutenticacaoViaTokenFilter(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	//Método qu eleva a logica de verificar se o Token é valido
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recuperarToken(request);
		
		boolean valido = tokenService.isTokenValido(token);
		
		System.out.println(valido);
		
		//Informar que pode seguir com a requisição
		filterChain.doFilter(request, response);
		
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(token == null || token.isEmpty() || !token.startsWith("Bearer "))
			return null;
		return token.substring(7, token.length());
	}

}
