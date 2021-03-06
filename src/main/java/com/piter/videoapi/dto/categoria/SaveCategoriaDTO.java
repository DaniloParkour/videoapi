package com.piter.videoapi.dto.categoria;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveCategoriaDTO {
	
	@NotNull
	private String titulo;
	
	@NotNull
	private String cor;

}
