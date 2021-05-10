package com.retrospective.controllers;

import com.retrospective.exceptions.ActionItemNotFoundException;
import com.retrospective.exceptions.ItemNotFoundException;
import com.retrospective.exceptions.RetrosNotFoundException;
import com.retrospective.models.ActionItem;
import com.retrospective.models.Item;
import com.retrospective.models.Retro;
import com.retrospective.services.RetrosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
//using Spring annotations to take care of boilerplate code and assign this class as an API controller
@RestController
//setting base endpoint for API endpoints
@RequestMapping(value = "/api")
public class RetrosController {
	
	//creating instance of the retrosService
	private final RetrosService retrosService;
	
	RetrosController(RetrosService retrosService) {
		this.retrosService = retrosService;
	}
	
	//GET method for displaying all retrospectives
	@GetMapping("/retros")
	ResponseEntity<List<Retro>> getAllRetros() {
		return new ResponseEntity<>(retrosService.getAllRetros(), HttpStatus.OK);
	}
	
	//POST method for creating a new retrospective
	@PostMapping("/retros")
	ResponseEntity<Retro> addNewRetro(@RequestBody Retro retro) {
		return new ResponseEntity<>(retrosService.addRetro(retro), HttpStatus.CREATED);
	}
	
	//GET method for displaying a retrospective by ID
	//with try catch and exception if the retrospective(ID) is not found
	@GetMapping("/retros/{id}")
	ResponseEntity<Retro> getRetroById(@PathVariable Long id) {
		try {
			return new ResponseEntity<>(retrosService.getRetroById(id), HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Retro not found " + id, ex);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//DELETE method for removing a retrospective by ID
	@DeleteMapping("/retros/{id}")
	ResponseEntity deleteRetroById(@PathVariable Long id) {
		retrosService.deleteRetroById(id);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	//PUT method for updating a retrospective by ID
	//with try catch and exception if the retrospective(ID) is not found
	@PutMapping("/retros/{id}")
	ResponseEntity<Retro> updateRetroById(@RequestBody Retro updatedRetro, @PathVariable Long id) {
		try {
			return new ResponseEntity<>(retrosService.updateRetroById(id, updatedRetro), HttpStatus.OK);
		} catch (RetrosNotFoundException e) {
			log.error("Retro not found exception", e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//GET method for displaying a retrospectives items by ID
	@GetMapping("/retros/{id}/items")
	ResponseEntity<List<Item>> getRetroItemsById(@PathVariable Long id) {
		return new ResponseEntity<>(retrosService.getRetroItemsById(id), HttpStatus.OK);
	}
	
	@GetMapping("/retros/{id}/items/sentiment-analysis")
	ResponseEntity<String> getRetroItemsByIdSentimentAnalysis(@PathVariable Long id) throws IOException {
		return new ResponseEntity<>(retrosService.getRetroItemsByIdSentimentAnalysis(id), HttpStatus.OK);
	}
	
	//DELETE method for removing a retrospectives item by ID
	@DeleteMapping("/items/{itemId}")
	ResponseEntity deleteRetroItemById(@PathVariable Long itemId) {
		retrosService.deleteRetroItemById(itemId);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	//POST method for creating a new item on a retrospective
	//with try catch and exception if the retrospective cannot be found
	@PostMapping("/retros/{id}/items")
	ResponseEntity<Item> addItemToRetro(@RequestBody Item item, @PathVariable Long id) {
		try {
			return new ResponseEntity<>(retrosService.addItemToRetro(id, item), HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Retro not found " + id, ex);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//PUT method for updating a retrospectives item by ID
	//with try catch and exception if the item id(ID) is not found
	@PutMapping("/items/{itemId}")
	ResponseEntity<Item> updateRetroItemById(@RequestBody Item updatedItem, @PathVariable Long itemId) {
		try {
			return new ResponseEntity<>(retrosService.updateRetroItemById(itemId, updatedItem), HttpStatus.OK);
		} catch (ItemNotFoundException e) {
			log.error("Item not found exception", e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//GET method for displaying a retrospective action items by ID
	@GetMapping("/retros/{id}/action-items")
	ResponseEntity<List<ActionItem>> getRetroActionItemsById(@PathVariable Long id) {
		return new ResponseEntity<>(retrosService.getRetroActionItemsById(id), HttpStatus.OK);
	}
	
	//POST method for creating a new action item on a retrospective
	//with try catch and exception if the retrospective cannot be found
	@PostMapping("/retros/{id}/action-items")
	ResponseEntity<ActionItem> addActionItemToRetro(@RequestBody ActionItem actionItem, @PathVariable Long id) {
		try {
			return new ResponseEntity<>(retrosService.addActionItemToRetro(id, actionItem), HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Retro not found " + id, ex);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//DELETE method for removing a retrospectives action item by ID
	@DeleteMapping("/action-items/{actionItemId}")
	ResponseEntity deleteRetroActionItemById(@PathVariable Long actionItemId) {
		retrosService.deleteRetroActionItemById(actionItemId);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	//PUT method for updating a retrospectives action item by ID
	//with try catch and exception if the action item id(ID) is not found
	@PutMapping("/action-items/{actionItemId}")
	ResponseEntity<ActionItem> updateRetroActionItemById(@RequestBody ActionItem updatedActionItem, @PathVariable Long actionItemId) {
		try {
			return new ResponseEntity<>(retrosService.updateRetroActionItemById(actionItemId, updatedActionItem), HttpStatus.OK);
		} catch (ActionItemNotFoundException e) {
			log.error("Action item not found exception", e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/retros/{id}/export-items")
	ResponseEntity<List<Item>> getRetroAndExportItemsToCSV(@PathVariable Long id, HttpServletResponse response) throws RetrosNotFoundException, IOException {
		System.out.println("Exporting items to CSV");
		return new ResponseEntity<>(retrosService.getRetroItemsAndExportToCSV(id, response), HttpStatus.OK);
	}
	
	@GetMapping("/retros/{id}/export-actionItems")
	ResponseEntity<List<ActionItem>> getRetroAndExportActionItemsToCSV(@PathVariable Long id, HttpServletResponse response) throws RetrosNotFoundException, IOException {
		System.out.println("Exporting action items to CSV");
		return new ResponseEntity<>(retrosService.getRetroActionItemsAndExportToCSV(id, response), HttpStatus.OK);
	}
	
	
}
