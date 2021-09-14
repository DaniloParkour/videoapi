package com.piter.videoapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.piter.videoapi.dto.SaveVideoDTO;
import com.piter.videoapi.dto.UpdateVideoDTO;
import com.piter.videoapi.dto.VideoDTO;
import com.piter.videoapi.exceptions.VideoNotFoundException;
import com.piter.videoapi.mapper.GenericMapper;
import com.piter.videoapi.mapper.requests.ResponseModel;
import com.piter.videoapi.model.Categoria;
import com.piter.videoapi.model.Video;
import com.piter.videoapi.repository.CategoriaRepository;
import com.piter.videoapi.repository.VideoRepository;

@Service
public class VideoService {
	
	@Autowired
	private VideoRepository repository;
	
	@Autowired
	private CategoriaRepository categoria_repository;
	
	@Autowired
	private GenericMapper mapper;
	
	public ResponseModel<VideoDTO> getVideo(Long id) {
		ResponseModel<VideoDTO> response = new ResponseModel<>("SUCCESS");
		
		Optional<Video> video = repository.findById(id);
		if(video.isPresent()) {
			response.setData(mapper.toObject(video.get(), VideoDTO.class));
			return response;
		}

		throw new VideoNotFoundException("ID informado não pertence a um registro no banco de dados");
	}
	
	public ResponseModel<VideoDTO> save(SaveVideoDTO video) {
		
		ResponseModel<VideoDTO> response;
		
		try {
			Video newVideo = repository.save(mapper.toObject(video, Video.class));
			response = new ResponseModel<>("SUCCESS");
			response.setData(mapper.toObject(newVideo, VideoDTO.class));	
		} catch (IllegalArgumentException e) {
			response = new ResponseModel<>("Error");
			response.setMessage("Can't save the video");
		}
		
		return response;
	}
	
	public ResponseModel<VideoDTO> listAll(Pageable pagination) {
		ResponseModel<VideoDTO> response;
		
		try {
			Page<Video> videos = repository.findAll(pagination);
			response = new ResponseModel<>("SUCCESS");
			response.setList(mapper.toList(videos.getContent(), VideoDTO.class));
		} catch (Exception e) {
			response = new ResponseModel<>("Error");
			response.setMessage("Can't list videos");
		}
		
		return response;
	}
	
	public ResponseModel<VideoDTO> findByTitulo(String value, Pageable pagination) {
		ResponseModel<VideoDTO> response;
		
		try {
			Page<Video> videos = repository.findByTituloLike(value, pagination);
			response = new ResponseModel<>("SUCCESS");
			response.setList(mapper.toList(videos.getContent(), VideoDTO.class));
		} catch (Exception e) {
			response = new ResponseModel<>("Error");
			response.setMessage("Can't list videos");
		}
		
		return response;
	}
	
	public ResponseModel<VideoDTO> update(UpdateVideoDTO video) {
		ResponseModel<VideoDTO> response;
		Optional<Video> videoBd = repository.findById(video.getId());
		
		if(videoBd.isPresent()) {
			try {
				if(video.getDescricao() != null) videoBd.get().setDescricao(video.getDescricao());
				if(video.getTitulo() != null) videoBd.get().setTitulo(video.getTitulo());
				if(video.getUrl() != null) videoBd.get().setUrl(video.getUrl());
				if(video.getCategoriaId() != null) {
					Categoria categoria = categoria_repository.getById(video.getCategoriaId());
					videoBd.get().setCategoria(categoria);
				}
				Video updateVideo = repository.save(videoBd.get());
				response = new ResponseModel<>("SUCCESS");
				response.setData(mapper.toObject(updateVideo, VideoDTO.class));
			} catch (Exception e) {
				response = new ResponseModel<>("Error");
				response.setMessage("Can't update video");
			}
			return response;
		}
		
		throw new VideoNotFoundException("ID informado não pertence a um registro no banco de dados");
		
	}

	public ResponseModel<Void> delete(Long id) {
		
		Optional<Video> videoBd = repository.findById(id);
		
		if(videoBd.isPresent()) {
			ResponseModel<Void> response;
			try {
				repository.deleteById(id);
				response = new ResponseModel<>("SUCCESS");
			} catch (Exception e) {
				response = new ResponseModel<>("Error");
				response.setMessage("Can't delete video");
			}
			
			return response;
		}
		
		throw new VideoNotFoundException("ID informado não pertence a um registro no banco de dados");
	}
}
