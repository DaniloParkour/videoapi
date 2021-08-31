package com.piter.videoapi.dto.categoria;

import com.piter.videoapi.dto.UpdateVideoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
	
	private String titulo;
	private String cor;

}
