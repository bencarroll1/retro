package com.retrospective.controllers;

import com.retrospective.models.ActionItem;
import com.retrospective.models.Item;
import com.retrospective.models.Retro;
import com.retrospective.services.RetrosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	
	//GET method for displaying a retrospectives items by ID
	@GetMapping("/retros/{id}/items")
	ResponseEntity<List<Item>> getRetroItemsById(@PathVariable Long id) {
		return new ResponseEntity<>(retrosService.getRetroItemsById(id), HttpStatus.OK);
	}
	
	//GET method for displaying a retrospective action items by ID
	@GetMapping("/retros/{id}/action-items")
	ResponseEntity<List<ActionItem>> getRetroActionItemsById(@PathVariable Long id) {
		return new ResponseEntity<>(retrosService.getRetroActionItemsById(id), HttpStatus.OK);
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
	
}
