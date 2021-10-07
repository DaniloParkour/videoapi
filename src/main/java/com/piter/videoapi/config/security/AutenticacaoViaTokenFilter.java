package com.piter.videoapi.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.piter.videoapi.model.Usuario;
import com.piter.videoapi.repository.AutenticacaoRepository;

												//Para chamad apenas uma vez a cada requisição
public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {
	
	// Não conseguimos injetar a dependencia aqui por AUTOWIRED
	private TokenService tokenService;
	
	private AutenticacaoRepository autenticacaoRepository;
	
	public AutenticacaoViaTokenFilter(TokenService tokenService, AutenticacaoRepository autenticacaoRepository) {
		this.tokenService = tokenService;
		this.autenticacaoRepository = autenticacaoRepository;
	}

	//Método qu eleva a logica de verificar se o Token é valido
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recuperarToken(request);
		
		boolean valido = tokenService.isTokenValido(token);
		
		if(valido) {
			autenticarUsuario(token);
		}
		
		//Informar que pode seguir com a requisição
		filterChain.doFilter(request, response);
		
	}

	private void autenticarUsuario(String token) {
		
		// Dentro do Token temos o ID do usuário pois definimos isso quando construímos o token
		Long idUsuario = tokenService.getIdUsuario(token);
		
		Usuario usuario =  autenticacaoRepository.findById(idUsuario).get();
		
		UsernamePasswordAuthenticationToken authentication = 
				new UsernamePasswordAuthenticationToken(
						usuario,
						null /*credenciais - senha - não precisa já está autenticado*/,
						usuario.getAuthorities()
				);
		
		//Falar para o Spring que o usuário já está autenticado
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(token == null || token.isEmpty() || !token.startsWith("Bearer "))
			return null;
		return token.substring(7, token.length());
	}

}
