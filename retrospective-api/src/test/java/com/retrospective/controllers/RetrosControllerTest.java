package com.retrospective.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retrospective.models.ActionItem;
import com.retrospective.models.Item;
import com.retrospective.models.Retro;
import com.retrospective.services.RetrosService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static com.retrospective.models.ItemType.*;
import static com.retrospective.models.ItemType.GOOD;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RetrosController.class)
class RetrosControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RetrosService retrosService;
	
	// test to get all retrospectives when the retrospectives exist
	// ensures the response returned from /api/retros/ matches the expected list and returns a 200 response code
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
	
	// test to get a retro by Id when it exists
	// ensures the returned response from /api/retros/{retroId} matches the expected retro and gives a 200 response code
	@Test
	void getRetroByIdWhenIdExists() throws Exception {
		// given
		Retro expectedRetrosList = new Retro("Retro one", false);
		String expectedJsonResponse = new ObjectMapper().writeValueAsString(expectedRetrosList);
		when(retrosService.getRetroById(1L)).thenReturn(expectedRetrosList);
		
		// when & then
		this.mockMvc.perform(get("/api/retros/1"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().json(expectedJsonResponse));
	}
	
	// test to get the items associated with a retro when the items exist
	// ensures the returned response from /api/retros/{retroId}/items matches the expected list of items
	// and gives a 200 response code
	@Test
	void getItemsByRetroIdWhenItemsExist() throws Exception {
		// given
		List<Item> expectedRetroItemsList = Arrays.asList(
				new Item("Item one", GOOD, false, 0, null),
				new Item("Item one", BAD, false, 2, null),
				new Item("Item one", QUESTION, false, -1, null)
		);
		String expectedJsonResponse = new ObjectMapper().writeValueAsString(expectedRetroItemsList);
		when(retrosService.getRetroItemsById(1L)).thenReturn(expectedRetroItemsList);
		
		// when & then
		this.mockMvc.perform(get("/api/retros/1/items"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().json(expectedJsonResponse));
	}
	
	// test to get the action items associated with a retro when the items exist
	// ensures the returned response from /api/retros/{retroId}/action-items matches the expected list of action items
	// and gives a 200 response code
	@Test
	void getActionItemsByRetroIdWhenActionItemsExist() throws Exception {
		// given
		List<ActionItem> expectedRetroActionItemsList = Arrays.asList(
				new ActionItem("Action Item one", null),
				new ActionItem("Action Item one", null),
				new ActionItem("Action Item one", null)
		);
		String expectedJsonResponse = new ObjectMapper().writeValueAsString(expectedRetroActionItemsList);
		when(retrosService.getRetroActionItemsById(1L)).thenReturn(expectedRetroActionItemsList);
		
		// when & then
		this.mockMvc.perform(get("/api/retros/1/action-items"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().json(expectedJsonResponse));
	}
	
	// test to create a new retrospective when the retrospective data is given (name, archive status)
	// ensures the returned response from /api/retros matches the expected retro
	// and gives a 200 response code
	@Test
	void postNewRetroWhenRetroGiven() throws Exception {
		// given
		Retro expectedRetro = new Retro("Retro one", true);
		String expectedJsonResponse = new ObjectMapper().writeValueAsString(expectedRetro);
		when(retrosService.addRetro(expectedRetro)).thenReturn(expectedRetro);
		
		// when & then
		this.mockMvc.perform(post("/api/retros").contentType(MediaType.APPLICATION_JSON)
				.content(expectedJsonResponse))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().json(expectedJsonResponse));
	}
	
	// test to add new items to an existing retro
	// test ensures that the returned response from /api/retros/{retroId}/items indicates that the item has been created
	// with a 200 response code
	@Test
	void postNewItemToExistingRetro() throws Exception {
		// given
		Item expectedItem = new Item("Item", GOOD, false, 4, null);
		String expectedJsonResponse = new ObjectMapper().writeValueAsString(expectedItem);
		when(retrosService.addItemToRetro(null, expectedItem)).thenReturn(expectedItem);
		
		// when & then
		this.mockMvc.perform(post("/api/retros/1/items").contentType(MediaType.APPLICATION_JSON)
				.content(expectedJsonResponse))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	// test to post a new action item to an existing retrospective
	// test ensures that the returned response from /api/retros/{retroId}/action-items indicates that the item has been created
	// with a 200 response code
	@Test
	void postNewActionItemToExistingRetro() throws Exception {
		// given
		ActionItem expectedActionItem = new ActionItem("Action Item", null);
		String expectedJsonResponse = new ObjectMapper().writeValueAsString(expectedActionItem);
		when(retrosService.addActionItemToRetro(null, expectedActionItem)).thenReturn(expectedActionItem);
		
		// when & then
		this.mockMvc.perform(post("/api/retros/1/action-items").contentType(MediaType.APPLICATION_JSON)
				.content(expectedJsonResponse))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
}
