package com.piter.videoapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveVideoDTO {
	
	@NonNull
	private String descricao;
	
	@NonNull
	private String titulo;
	
	@NonNull
	private String url;

}
