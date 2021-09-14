package com.piter.videoapi.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.piter.videoapi.model.Video;

@RunWith(SpringRunner.class)
@DataJpaTest //Para classes de teste específicos para Repositories
public class VideoRepositoryTest {
	
	@Autowired
	private VideoRepository repository;

	@Test
	public void shouldReturnOneVideoWhenSearchForTitleAva() {
		String ava = "Ava";
		List<Video> video = repository.findByTituloLike(ava);
		Assert.assertNotNull(video);
	}

}
