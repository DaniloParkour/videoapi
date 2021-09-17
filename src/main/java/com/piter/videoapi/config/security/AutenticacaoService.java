package com.piter.videoapi.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.piter.videoapi.model.Usuario;
import com.piter.videoapi.repository.AutenticacaoRepository;

@Service                            // Falar ao SPRING que essa é service da logica de autenticacao
public class AutenticacaoService implements UserDetailsService {
	
	@Autowired
	private AutenticacaoRepository repository;

	@Override // A checagem da SENHA o Spring Boot faz em memória
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Usuario> usuario = repository.findByEmail(username);
		
		if(usuario.isPresent())
			return usuario.get();
		
		throw new UsernameNotFoundException("Usuário não encontrado");
	}

}
