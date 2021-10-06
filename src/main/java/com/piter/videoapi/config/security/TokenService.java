package com.piter.videoapi.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.piter.videoapi.model.Usuario;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${videosapi.jwt.secret}") // Para injetar conteúdo do application.properties
	private String secret;
	
	@Value("${videosapi.jwt.expiration}") // Para injetar conteúdo do application.properties
	private String expiration;
	
	public String gerarToken(Authentication authentication) {
		
		Usuario user = (Usuario) authentication.getPrincipal();
		
		Date agora = new Date();
		Date dataExpiracao = new Date(agora.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("videoapi") // Quem é a aplicação que gerou o Token
				.setSubject(user.getId().toString()) // Quem é usuário dono do token
				.setIssuedAt(agora) // Quando que o Token foi criado
				.setExpiration(dataExpiracao) // Data que o Token vai expirar
				.signWith(SignatureAlgorithm.HS256, secret) // Criptografar o Token (Algoritimo, Senha)
				.compact()
				;
	}

	public boolean isTokenValido(String token) {
		
		try {
			// O "parseClaimsJws" retorna um objeto se o token for válido, se não ele joga uma EXCEPTION
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
