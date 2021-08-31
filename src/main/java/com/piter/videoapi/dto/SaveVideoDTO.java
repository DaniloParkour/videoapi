package com.piter.videoapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveVideoDTO {
	
	private String descricao;
	
	private String titulo;
	
	private String url;

}
