package com.piter.videoapi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.piter.videoapi.model.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
	
	List<Video> findByCategoriaId(Long id_category);

	Page<Video> findByTituloLike(String value, Pageable pagination);

}
