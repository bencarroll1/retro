package com.retrospective.services;

import com.retrospective.exceptions.RetrosNotFoundException;
import com.retrospective.models.ActionItem;
import com.retrospective.models.Item;
import com.retrospective.models.ItemType;
import com.retrospective.models.Retro;
import com.retrospective.repositories.ActionItemsRepository;
import com.retrospective.repositories.ItemsRepository;
import com.retrospective.repositories.RetrosRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class RetrosServiceTest {
	@MockBean
	private RetrosRepository retrosRepository;
	
	@MockBean
	private ItemsRepository itemsRepository;
	
	@MockBean
	private ActionItemsRepository actionItemsRepository;
	
	@Autowired
	private RetrosService retroService;
	
	// test to get all retros from the retros repository when they exist there
	// passes by list sizes matching by using assertions
	@Test
	void getAllRetrosWhenRetrosExist() {
		// given
		List<Retro> expectedRetrosList = Arrays.asList(
				new Retro("Retro one", false),
				new Retro("Retro two", true),
				new Retro("Retro three", false)
		);
		when(retrosRepository.findAll()).thenReturn(expectedRetrosList);
		
		// when
		List<Retro> actualRetrosList = retroService.getAllRetros();
		
		// then
		Assertions.assertEquals(expectedRetrosList.size(), actualRetrosList.size());
	}
	
	// test to get all retros when no retros exist in retros repo
	// returns both as empty if successful, done using assertions
	@Test
	void getAllRetrosWhenNoRetrosExist() {
		// given
		List<Retro> expectedRetrosList = Arrays.asList();
		when(retrosRepository.findAll()).thenReturn(expectedRetrosList);
		
		// when
		List<Retro> actualRetrosList = retroService.getAllRetros();
		
		//then
		Assertions.assertEquals(expectedRetrosList.size(), actualRetrosList.size());
	}
	
	// test to get retro by id when the retro exists in retro repo
	// passes by actual retro not being null i.e. it exists
	@Test
	void getRetroByIdWhenRetroExists() throws RetrosNotFoundException {
		// given
		Optional<Retro> expectedRetro = Optional.of(new Retro("Retro one", true));
		when(retrosRepository.findById(1L)).thenReturn(expectedRetro);
		
		// when
		Retro actualRetro = retroService.getRetroById(1L);
		
		// then
		Assertions.assertNotNull(actualRetro);
	}
	
	// test to get a retro by id from retros repo when it does not exist
	// passes by assertion that the retro not found exception is thrown because non-existent retro cannot be found
	@Test
	void getRetroByIdWhenRetroDoesNotExist() {
		// given
		Optional<Retro> expectedRetro = Optional.empty();
		when(retrosRepository.findById(1L)).thenReturn(expectedRetro);
		
		// when & then
		Assertions.assertThrows(RetrosNotFoundException.class, () -> retroService.getRetroById(1L));
	}
	
	// test to get a retros items from item repo when they exist there
	// passes by asserting that the returned list of items matches the expected one, i.e. items exist
	@Test
	void getRetroItemsByIdWhenItemsExist() {
		//given
		List<Item> expectedItemsList = Arrays.asList(
				new Item("Item Number 1", ItemType.GOOD, false, 4, null),
				new Item("Item Number 2", ItemType.BAD, false, -2, null),
				new Item("Item Number 3", ItemType.QUESTION, false, 0, null)
		);
		when(itemsRepository.findAllByRetroId(any())).thenReturn(expectedItemsList);
		
		//when
		List<Item> actualItemsList = retroService.getRetroItemsById(1L);
		
		//then
		Assertions.assertEquals(expectedItemsList.size(), actualItemsList.size());
	}
	
	// test to get a retros items from items repo when they do not exist
	// passes by assertion that empty expected list of items matches the size of that returned
	@Test
	void getRetroItemsByIdWhenItemsDoNotExist() {
		//given
		List<Item> expectedItemsList = Arrays.asList();
		when(itemsRepository.findAllByRetroId(1L)).thenReturn(expectedItemsList);
		
		//when
		List<Item> actualItemsList = retroService.getRetroItemsById(1L);
		
		//then
		Assertions.assertEquals(expectedItemsList.size(), actualItemsList.size());
	}
	
	// test to get a retros action items from action item repo when they exist there
	// passes by asserting that the returned list of action items matches the expected one, i.e. action items exist
	@Test
	void getRetroActionItemsByIdWhenActionItemsExist() {
		//given
		List<ActionItem> expectedActionItemsList = Arrays.asList(
				new ActionItem("Action Item Number 1", null),
				new ActionItem("Action Item Number 2", null),
				new ActionItem("Action Item Number 3", null)
		);
		when(actionItemsRepository.findAllByRetroId(any())).thenReturn(expectedActionItemsList);
		
		//when
		List<ActionItem> actualActionItemsList = retroService.getRetroActionItemsById(1L);
		
		//then
		Assertions.assertEquals(expectedActionItemsList.size(), actualActionItemsList.size());
	}
	
	// test to get a retros action items from action items repo when they do not exist
	// passes by assertion that empty expected list of action items matches the size of that returned
	@Test
	void getRetroActionItemsByIdWhenActionItemsDoNotExist() {
		//given
		List<ActionItem> expectedActionItemsList = Arrays.asList();
		when(actionItemsRepository.findAllByRetroId(any())).thenReturn(expectedActionItemsList);
		
		//when
		List<ActionItem> actualActionItemsList = retroService.getRetroActionItemsById(1L);
		
		//then
		Assertions.assertEquals(expectedActionItemsList.size(), actualActionItemsList.size());
	}
	
	// test to create a new retro when the data is supplied to do so
	// passes by expected retro matches actually created retro
	@Test
	void postNewRetroWhenRetroGiven() {
		// given
		Retro expectedRetro = new Retro("First Retrospective", false);
		when(retroService.addRetro(expectedRetro)).thenReturn(expectedRetro);
		
		// when
		Retro actualRetro = retroService.addRetro(expectedRetro);
		
		//then
		Assertions.assertEquals(expectedRetro, actualRetro);
	}
	
	// test to create a retro item (and add to retro) when the data is supplied to do so
	// passes when retro retros expected and actual item have same size i.e. item has been added to retro
	@Test
	void postItemByIdWhenRetroExist() throws RetrosNotFoundException {
		//given
		Retro expectedRetro = new Retro("Retro one", true);
		when(retrosRepository.findById(1L)).thenReturn(Optional.of(expectedRetro));
		
		Item expectedItem = new Item("Fourth Item", ItemType.GOOD, false, -1, expectedRetro);
		when(retroService.addItemToRetro(1L, expectedItem)).thenReturn(expectedItem);
		
		// when
		Retro actualRetro = retroService.getRetroById(1L);
		Item actualItem = retroService.addItemToRetro(1L, expectedItem);
		
		//then
		Assertions.assertNotNull(actualRetro);
		Assertions.assertEquals(expectedItem, actualItem);
	}
	
	// test to create a retro item when retro does not exist
	// passes when retro not found exception is thrown as no retro has been created to add item to
	@Test
	void postItemByIdWhenRetroDoesNotExist() throws RetrosNotFoundException {
		//given
		Retro expectedRetro = new Retro();
		when(retrosRepository.findById(1L)).thenReturn(Optional.of(expectedRetro));
		
		Item expectedItem = new Item("Fourth Item", ItemType.GOOD, false, 2, expectedRetro);
		when(retroService.addItemToRetro(1L, expectedItem)).thenReturn(expectedItem);
		
		// when
		Retro actualRetro = retroService.getRetroById(1L);
		Item actualItem = retroService.addItemToRetro(1L, expectedItem);
		
		//then
		Assertions.assertNull(actualRetro.getId(), actualRetro.getName());
	}
	
	// test to create a retro action item (and add to retro) when the data is supplied to do so
	// passes when retro retros expected and actual action item have same size i.e. action item has been added to retro
	@Test
	void postActionItemByIdWhenRetroExists() throws RetrosNotFoundException {
		//given
		Retro expectedRetro = new Retro("Retro one", true);
		when(retrosRepository.findById(1L)).thenReturn(Optional.of(expectedRetro));
		
		ActionItem expectedActionItem = new ActionItem("Fourth Action Item", expectedRetro);
		when(retroService.addActionItemToRetro(1L, expectedActionItem)).thenReturn(expectedActionItem);
		
		// when
		Retro actualRetro = retroService.getRetroById(1L);
		ActionItem actualActionItem = retroService.addActionItemToRetro(1L, expectedActionItem);
		
		//then
		Assertions.assertNotNull(actualRetro);
		Assertions.assertEquals(expectedActionItem, actualActionItem);
	}
	
	// test to create a retro action item when retro does not exist
	// passes when retro not found exception is thrown as no retro has been created to add action item to
	@Test
	void postActionItemByIdWhenRetroDoesNotExist() throws RetrosNotFoundException {
		//given
		Retro expectedRetro = new Retro();
		when(retrosRepository.findById(1L)).thenReturn(Optional.of(expectedRetro));
		
		ActionItem expectedActionItem = new ActionItem("Fourth Item", expectedRetro);
		when(retroService.addActionItemToRetro(1L, expectedActionItem)).thenReturn(expectedActionItem);
		
		// when
		Retro actualRetro = retroService.getRetroById(1L);
		ActionItem actualActionItem = retroService.addActionItemToRetro(1L, expectedActionItem);
		
		//then
		Assertions.assertNull(actualRetro.getId(), "id was not null");
		Assertions.assertNull(actualRetro.getName(), "name was not null");
	}
}
