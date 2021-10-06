package com.piter.videoapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piter.videoapi.config.security.TokenService;
import com.piter.videoapi.dto.LoginDTO;
import com.piter.videoapi.dto.TokenDTO;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
	
	// O SPRING já tem uma classe para gerenciar a autenticação
	@Autowired // Precisamos configurar para o SPRING aprender a fazer a injeção de dependências (SrcuriryConfiguration)
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<TokenDTO> autenticar(@RequestBody @Valid LoginDTO loginData) {
		UsernamePasswordAuthenticationToken dadosLogin = 
				new UsernamePasswordAuthenticationToken(loginData.getEmail(), loginData.getSenha());
		try {
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = tokenService.gerarToken(authentication);
			return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
		
	}

}
