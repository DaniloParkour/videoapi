package com.piter.videoapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piter.videoapi.dto.categoria.CategoriaDTO;
import com.piter.videoapi.mapper.requests.ResponseModel;
import com.piter.videoapi.service.CategoriaService;

import io.swagger.annotations.Api;

@Api(tags = "Categorias")
@RestController
@RequestMapping("/categorias")
public class CategoriasController {
	
	@Autowired
	private CategoriaService service;
	
	@GetMapping
	public ResponseModel<CategoriaDTO> listAll() {
		return service.listAll();
	}
	
}
