package com.piter.videoapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.piter.videoapi.dto.SaveVideoDTO;
import com.piter.videoapi.dto.UpdateVideoDTO;
import com.piter.videoapi.dto.VideoDTO;
import com.piter.videoapi.mapper.requests.ResponseModel;
import com.piter.videoapi.service.VideoService;

import io.swagger.annotations.Api;

@Api(tags = "Videos")
@RestController
@RequestMapping("/videos")
public class VideosController {
	
	@Autowired
	private VideoService service;
	
	@GetMapping("/{id}")
	public ResponseModel<VideoDTO> getVideo(@PathVariable("id") Long id) {
		return service.getVideo(id);
	}
	
	@GetMapping()
	public ResponseModel<VideoDTO> listAll(
										@RequestParam(required = false, defaultValue = "0") int page,
										@RequestParam(required = false, defaultValue = "10") Integer size
									) {
		Pageable pagination = PageRequest.of(page, size != null ? size : 5);
		return service.listAll(pagination);
	}
	
	@PostMapping
	public ResponseModel<VideoDTO> save(@Valid @RequestBody SaveVideoDTO video) {
		return service.save(video);
	}
	
	@PutMapping
	public ResponseModel<VideoDTO> update(@RequestBody UpdateVideoDTO video) {
		return service.update(video);
	}
	
	@DeleteMapping("/{id}")
	public ResponseModel<Void> deletar(@PathVariable("id") Long id) {
		return service.delete(id);
	}
	
	@GetMapping("/")
	public ResponseModel<VideoDTO> findByTitulo(
											@RequestParam(name = "search") String search,
											@RequestParam int page,
											@RequestParam(required = false) Integer size
										) {
		Pageable pagination = PageRequest.of(page, size != null ? size : 5);
		
		search = search.startsWith("%") ? search : "%" + search;
		search = search.endsWith("%") ? search : search + "%";
		return service.findByTitulo(search, pagination);
	}

}
