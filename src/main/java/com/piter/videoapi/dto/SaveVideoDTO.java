package com.piter.videoapi.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveVideoDTO {
	
	private Long usuarioID;
	
	private String descricao;
	
	@NotNull
	private String titulo;
	
	@NotNull
	private String url;

}
