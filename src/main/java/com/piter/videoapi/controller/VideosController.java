package com.piter.videoapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.piter.videoapi.dto.SaveVideoDTO;
import com.piter.videoapi.dto.UpdateVideoDTO;
import com.piter.videoapi.dto.VideoDTO;
import com.piter.videoapi.exceptions.TokenUserNotIdentified;
import com.piter.videoapi.mapper.requests.ResponseModel;
import com.piter.videoapi.service.VideoService;

import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;

@Api(tags = "Videos")
@RestController
@RequestMapping("/videos")
public class VideosController {
	
	@Value("${videosapi.jwt.secret}") // Para injetar conteúdo do application.properties
	private String secret;
	
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
	public ResponseModel<VideoDTO> save(@RequestHeader HttpHeaders headers, @Valid @RequestBody SaveVideoDTO video) {
		String token = headers.getFirst("Authorization");
		if(token != null && !token.isEmpty() && token.startsWith("Bearer ")) {
			String tokenValue = token.substring(7, token.length());
			String userId = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(tokenValue).getBody().getSubject();
			video.setUsuarioID(Long.parseLong(userId));
			
			return service.save(video);
		} else {
			throw new TokenUserNotIdentified("Invalid Token User ID!");
		}
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
