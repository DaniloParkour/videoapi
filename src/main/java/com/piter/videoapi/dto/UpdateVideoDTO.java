package com.piter.videoapi.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateVideoDTO {
	
	@NotNull
	private Long id;
	
	private Long categoriaId;
	
	private String descricao;
	
	private String titulo;
	
	private String url;

}
