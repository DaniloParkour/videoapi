package com.piter.videoapi.controller;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc // Para consegiuiir injetar o MockMvc no teste
// Caso queira simular o controller
// @WebMvcTest //Para o Spring Carregar As Classes Do MVC (necessário para Testes de controllers)
@ActiveProfiles("test")
public class VideosControllerTest {

	@Autowired //Injetar uma classe mock que simula a requisição MVC
	private MockMvc mockMvc;
	
	@Test
	public void shouldListVideos() throws Exception {
		URI uri = new URI("/videos");
	
		mockMvc.perform(MockMvcRequestBuilders
				.get(uri)
				).andExpect(MockMvcResultMatchers
						.status()
						.is(200)
						);
	}
	// .post(uri)
	// .content("Json Content")
	// .contentType(MediaType.APPLICATION_JSON)
}
