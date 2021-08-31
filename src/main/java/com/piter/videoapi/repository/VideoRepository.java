package com.piter.videoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.piter.videoapi.model.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

}
