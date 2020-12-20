package com.retrospective.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retrospective.models.Retro;
import com.retrospective.services.RetrosService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RetrosController.class)
class RetrosControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RetrosService retrosService;
	
	@Test
	void getAllRetrosWhenRetrosExist() throws Exception {
		// given
		List<Retro> expectedRetrosList = Arrays.asList(
				new Retro("Retro one", true),
				new Retro("Retro two", false),
				new Retro("Retro three", true)
		);
		String expectedJsonResponse = new ObjectMapper().writeValueAsString(expectedRetrosList);
		when(retrosService.getAllRetros()).thenReturn(expectedRetrosList);
		
		// when & then
		this.mockMvc.perform(get("/api/retros"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().json(expectedJsonResponse));
	}
}
