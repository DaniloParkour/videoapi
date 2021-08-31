package com.piter.videoapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piter.videoapi.dto.categoria.CategoriaDTO;
import com.piter.videoapi.mapper.GenericMapper;
import com.piter.videoapi.mapper.requests.ResponseModel;
import com.piter.videoapi.model.Categoria;
import com.piter.videoapi.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;
	
	@Autowired
	private GenericMapper mapper;
	
	public ResponseModel<CategoriaDTO> listAll() {
		ResponseModel<CategoriaDTO> response;
		
		try {
			List<Categoria> categorias = repository.findAll();
			response = new ResponseModel<>("SUCCESS");
			response.setList(mapper.toList(categorias, CategoriaDTO.class));
		} catch (Exception e) {
			response = new ResponseModel<>("Error");
			response.setMessage("Can't list categories");
		}
		
		return response;
	}

}
