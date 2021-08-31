package com.piter.videoapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piter.videoapi.dto.SaveVideoDTO;
import com.piter.videoapi.dto.UpdateVideoDTO;
import com.piter.videoapi.dto.VideoDTO;
import com.piter.videoapi.exceptions.VideoNotFoundException;
import com.piter.videoapi.mapper.GenericMapper;
import com.piter.videoapi.mapper.requests.ResponseModel;
import com.piter.videoapi.model.Video;
import com.piter.videoapi.repository.VideoRepository;

@Service
public class VideoService {
	
	@Autowired
	private VideoRepository repository;
	
	@Autowired
	private GenericMapper mapper;
	
	public ResponseModel<VideoDTO> getVideo(Long id) {
		ResponseModel<VideoDTO> response = new ResponseModel<>("SUCESSO");
		
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
			response = new ResponseModel<>("SUCESSO");
			response.setData(mapper.toObject(newVideo, VideoDTO.class));	
		} catch (IllegalArgumentException e) {
			response = new ResponseModel<>("Error");
			response.setMessage("Can't save the video");
		}
		
		return response;
	}
	
	public ResponseModel<VideoDTO> listAll() {
		ResponseModel<VideoDTO> response;
		
		try {
			List<Video> videos = repository.findAll();
			response = new ResponseModel<>("SUCCESS");
			response.setList(mapper.toList(videos, VideoDTO.class));
		} catch (Exception e) {
			response = new ResponseModel<>("Error");
			response.setMessage("Can't list videos");
		}
		
		return response;
	}
	
	public ResponseModel<VideoDTO> atualizar(UpdateVideoDTO video) {
		ResponseModel<VideoDTO> response;
		
		try {
			Video updateVideo = repository.save(mapper.toObject(video, Video.class));
			response = new ResponseModel<>("SUCCESS");
			response.setData(mapper.toObject(updateVideo, VideoDTO.class));
		} catch (Exception e) {
			response = new ResponseModel<>("Error");
			response.setMessage("Can't update video");
		}
		
		return response;
	}

	public ResponseModel<Void> delete(Long id) {
		ResponseModel<Void> response = new ResponseModel<>("SUCESSO");
		repository.deleteById(id);
		
		return response;
	}

}
