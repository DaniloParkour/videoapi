package com.piter.videoapi.dto.categoria;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoriaDTO {
	
	@NotNull
	private Long id;
	
	private Long categoria_id;
	
	private String titulo;
	
	private String cor;

}
