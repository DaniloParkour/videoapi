package com.piter.videoapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.piter.videoapi.model.Categoria;
import com.piter.videoapi.model.Video;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
}
