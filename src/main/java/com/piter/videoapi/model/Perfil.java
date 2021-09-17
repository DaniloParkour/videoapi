package com.piter.videoapi.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor			// Interface que guarda as permissões de acesso
public class Perfil implements GrantedAuthority {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	// @OneToMany(mappedBy = "perfil")
	// private List<Usuario> usuarios;
	// Agora será ManyToMany e está defindo na classe Usuario

	@Override
	public String getAuthority() {
		return this.nome;
	}

}
