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
@RestController
@RequestMapping(value = "/api")
public class RetrosController {
	
	private final RetrosService retrosService;
	
	RetrosController(RetrosService retrosService) {
		this.retrosService = retrosService;
	}
	
	@GetMapping("/retros")
	ResponseEntity<List<Retro>> getAllRetros() {
		return new ResponseEntity<>(retrosService.getAllRetros(), HttpStatus.OK);
	}
	
	@PostMapping("/retros")
	ResponseEntity<Retro> addNewRetro(@RequestBody Retro retro) {
		return new ResponseEntity<>(retrosService.addRetro(retro), HttpStatus.CREATED);
	}
	
	@GetMapping("/retros/{id}")
	ResponseEntity<Retro> getRetroById(@PathVariable Long id) {
		try {
			return new ResponseEntity<>(retrosService.getRetroById(id), HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Retro not found " + id, ex);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/retros/{id}/items")
	ResponseEntity<List<Item>> getRetroItemsById(@PathVariable Long id) {
		return new ResponseEntity<>(retrosService.getRetroItemsById(id), HttpStatus.OK);
	}
	
	@GetMapping("/retros/{id}/action-items")
	ResponseEntity<List<ActionItem>> getRetroActionItemsById(@PathVariable Long id) {
		return new ResponseEntity<>(retrosService.getRetroActionItemsById(id), HttpStatus.OK);
	}
	
	@PostMapping("/retros/{id}/items")
	ResponseEntity<Item> addItemToRetro(@RequestBody Item item, @PathVariable Long id) {
		try {
			return new ResponseEntity<>(retrosService.addItemToRetro(id, item), HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Retro not found " + id, ex);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
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
