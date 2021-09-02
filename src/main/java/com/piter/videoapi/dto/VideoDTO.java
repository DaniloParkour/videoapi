package com.piter.videoapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTO {
	
	private String descricao;
	
	private Long categoriaId;
	
	private String titulo;
	
	private String url;

}
