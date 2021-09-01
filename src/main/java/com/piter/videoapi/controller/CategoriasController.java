package com.piter.videoapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piter.videoapi.dto.categoria.CategoriaDTO;
import com.piter.videoapi.dto.categoria.SaveCategoriaDTO;
import com.piter.videoapi.dto.categoria.UpdateCategoriaDTO;
import com.piter.videoapi.mapper.requests.ResponseModel;
import com.piter.videoapi.service.CategoriaService;

import io.swagger.annotations.Api;

@Api(tags = "Categorias")
@RestController
@RequestMapping("/categorias")
public class CategoriasController {
	
	@Autowired
	private CategoriaService service;
	
	@PostMapping
	public ResponseModel<CategoriaDTO> save(@Valid @RequestBody SaveCategoriaDTO categoria) {
		return service.save(categoria);
	}
	
	@GetMapping
	public ResponseModel<CategoriaDTO> listAll() {
		return service.listAll();
	}
	
	@GetMapping("/{id}")
	public ResponseModel<CategoriaDTO> getCategoria(@PathVariable("id") Long id) {
		return service.getCategoria(id);
	}
	
	@PutMapping
	public ResponseModel<CategoriaDTO> updateCategoria(@Valid @RequestBody UpdateCategoriaDTO categoria) {
		return service.updateCategoria(categoria);
	}
	
	@DeleteMapping("/{id}")
	public ResponseModel<Void> delete(@PathVariable("id") Long id) {
		return service.delete(id);
	}	
}
