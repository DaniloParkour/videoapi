package com.piter.videoapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piter.videoapi.dto.categoria.CategoriaDTO;
import com.piter.videoapi.dto.categoria.SaveCategoriaDTO;
import com.piter.videoapi.dto.categoria.UpdateCategoriaDTO;
import com.piter.videoapi.exceptions.CategoriaNotFoundException;
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
	
	public ResponseModel<CategoriaDTO> save(SaveCategoriaDTO categoria) {
		ResponseModel<CategoriaDTO> response;
		
		try {
			Categoria newCategoria = repository.save(mapper.toObject(categoria, Categoria.class));
			response = new ResponseModel<>("SUCCESS");
			response.setData(mapper.toObject(newCategoria, CategoriaDTO.class));
		} catch (Exception e) {
			response = new ResponseModel<>("Error");
			response.setMessage("Can't save the category");
			
		}
		
		return response;
	}
	
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
	
	public ResponseModel<CategoriaDTO> getCategoria(Long id) {
		
		ResponseModel<CategoriaDTO> response = new ResponseModel<>("SUCCESS");
		
		Optional<Categoria> categoria = repository.findById(id);
		
		if(categoria.isPresent()) {
			response.setData(mapper.toObject(categoria.get(), CategoriaDTO.class));
			return response;
		}
		
		throw new CategoriaNotFoundException("ID informado não pertence a um registro no banco de dados");
	}
	
	public ResponseModel<CategoriaDTO> updateCategoria(UpdateCategoriaDTO categoria) {
		
		ResponseModel<CategoriaDTO> response = new ResponseModel<>("SUCCESS");
		
		Optional<Categoria> categoriaBD = repository.findById(categoria.getId());
		
		if(categoriaBD.isPresent()) {
			if(categoria.getCor() != null) categoriaBD.get().setCor(categoria.getCor());
			if(categoria.getTitulo() != null) categoriaBD.get().setTitulo(categoria.getTitulo());
			Categoria updateCategoria = repository.save(categoriaBD.get());
			response = new ResponseModel<>("SUCCESS");
			response.setData(mapper.toObject(updateCategoria, CategoriaDTO.class));
			return response;
		}
		
		throw new CategoriaNotFoundException("ID informado não pertence a um registro no banco de dados");

	}
	
	public ResponseModel<Void> delete(Long id) {
		Optional<Categoria> categoriaBD = repository.findById(id);
		
		if(categoriaBD.isPresent()) {
			ResponseModel<Void> response;
			try {
				repository.deleteById(id);
				response = new ResponseModel<>("SUCCESS");
			} catch (Exception e) {
				response = new ResponseModel<>("Error");
				response.setMessage("Can't delete categoria");
			}
			return response;
		} else {
			throw new CategoriaNotFoundException("ID informado não pertence a um registro no banco de dados");
		}
	}
	
}
