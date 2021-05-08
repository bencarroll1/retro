package com.retrospective.services;

import com.retrospective.exceptions.ActionItemNotFoundException;
import com.retrospective.exceptions.ItemNotFoundException;
import com.retrospective.exceptions.RetrosNotFoundException;
import com.retrospective.models.ActionItem;
import com.retrospective.models.Item;
import com.retrospective.models.Retro;
import com.retrospective.repositories.ActionItemsRepository;
import com.retrospective.repositories.ItemsRepository;
import com.retrospective.repositories.RetrosRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//service annotation
//service layer stores business logic
@Service
public class RetrosService {
	//creating an instance of each repo
	private final RetrosRepository retrosRepository;
	private final ItemsRepository itemsRepository;
	private final ActionItemsRepository actionItemsRepository;
	
	RetrosService(RetrosRepository retrosRepository, ItemsRepository itemsRepository, ActionItemsRepository actionItemsRepository) {
		this.retrosRepository = retrosRepository;
		this.itemsRepository = itemsRepository;
		this.actionItemsRepository = actionItemsRepository;
	}
	
	//method to get all retrospectives from retros repo. method called by controller
	public List<Retro> getAllRetros() {
		return retrosRepository.findAll();
	}
	
	//method to add a retrospective to retros repo. method called by controller
	public Retro addRetro(Retro retro) {
		return retrosRepository.save(retro);
	}
	
	//method to get a retrospective by ID from retros repo. method called by controller
	public Retro getRetroById(Long id) throws RetrosNotFoundException {
		return retrosRepository.findById(id)
				.orElseThrow(RetrosNotFoundException::new);
	}
	
	// method to delete a retrospective by Id
	public void deleteRetroById(Long id) {
		retrosRepository.deleteById(id);
	}
	
	// method to update the contents of a retrospective by ID
	public Retro updateRetroById(@PathVariable Long id, @RequestBody Retro updatedRetro) throws RetrosNotFoundException {
		Optional<Retro> currentRetro = retrosRepository.findById(id);
		if (currentRetro.isPresent()) {
			
			updatedRetro.setId(currentRetro.get().getId());
		} else {
			throw new RetrosNotFoundException();
		}
		return retrosRepository.save(updatedRetro);
	}
	
	//method to get a retrospectives items from items repo. method called by controller
	public List<Item> getRetroItemsById(Long id) {
		return itemsRepository.findAllByRetroId(id);
	}
	
	// method to delete a retro item by id
	public void deleteRetroItemById(Long itemId) {
		itemsRepository.deleteById(itemId);
	}
	
	//method to get a retrospectives action items from action items repo. method called by controller
	public List<ActionItem> getRetroActionItemsById(Long id) {
		return actionItemsRepository.findAllByRetroId(id);
	}
	
	// method to delete a retro action item by id
	public void deleteRetroActionItemById(Long actionItemId) {
		actionItemsRepository.deleteById(actionItemId);
	}
	
	//method to add a retrospective item to items repo. method called by controller
	public Item addItemToRetro(Long id, Item item) throws RetrosNotFoundException {
		Retro retro = getRetroById(id);
		item.setRetro(retro);
		return itemsRepository.save(item);
	}
	
	//method to add a retrospective action item to items repo. method called by controller
	public ActionItem addActionItemToRetro(Long id, ActionItem actionItem) throws RetrosNotFoundException {
		Retro retro = getRetroById(id);
		actionItem.setRetro(retro);
		return actionItemsRepository.save(actionItem);
	}
	
	// method to update a retros items contents by id
	public Item updateRetroItemById(@PathVariable Long itemId, @RequestBody Item updatedItem) throws ItemNotFoundException {
		Optional<Item> currentItem = itemsRepository.findById(itemId);
		if (currentItem.isPresent()) {
			updatedItem.setRetro(currentItem.get().getRetro());
		} else {
			throw new ItemNotFoundException();
		}
		return itemsRepository.save(updatedItem);
	}
	
	// method to update a retros action items contents by id
	public ActionItem updateRetroActionItemById(@PathVariable Long actionItemId, @RequestBody ActionItem updatedActionItem) throws ActionItemNotFoundException {
		Optional<ActionItem> currentActionItem = actionItemsRepository.findById(actionItemId);
		if (currentActionItem.isPresent()) {
			updatedActionItem.setRetro(currentActionItem.get().getRetro());
		} else {
			throw new ActionItemNotFoundException();
		}
		return actionItemsRepository.save(updatedActionItem);
	}
	
	public List<Item> getRetroItemsByIdSentimentAnalysis(Long id) throws IOException {
		String retrospectiveItem;
		
		ArrayList<String> stopWords = new ArrayList<>();
		BufferedReader stop = new BufferedReader(new FileReader("src/main/resources/Dictionaries/stopWords.txt"));
		String line;
		while ((line = stop.readLine()) != null) {
			stopWords.add(line);
		}
		
		Map<String, String> map = new HashMap<>();
		BufferedReader in = new BufferedReader(new FileReader("src/main/resources/Dictionaries/AFINN-en-165.txt"));
		
		while ((line = in.readLine()) != null) {
			String[] parts = line.split("\t");
			map.put(parts[0], parts[1]);
		}
		in.close();
		
		List<Item> retroItems = itemsRepository.findAllByRetroId(id);
		String retroItemDescriptionWords = retroItems.toString().replaceAll("[^a-zA-Z]", " ");
		String trimmedRetroItemDescriptionWords = retroItemDescriptionWords.trim();
		if (trimmedRetroItemDescriptionWords.contains("Item")) {
			trimmedRetroItemDescriptionWords.replace("Item", "");
		}
//		List<String> retroItemDescriptionWords = new ArrayList<>();
//		//string tokenizer, split by commas? or =?
//		String[] tokens = trimmed.split(" ");
//		retroItemDescriptionWords.add(Arrays.toString(tokens));
//		retroItemDescriptionWords.removeIf(word -> word.equals(" "));
		
		float retrospectiveItemScore = 0;
		retrospectiveItem = trimmedRetroItemDescriptionWords;
		String[] word = retrospectiveItem.split(" ");
		
		for (int i = 0; i < word.length; i++) {
			if (stopWords.contains(word[i].toLowerCase())) {
			
			} else {
				if (map.get(word[i]) != null) {
					String wordScore = map.get(word[i].toLowerCase());
					retrospectiveItemScore = retrospectiveItemScore + Integer.parseInt(wordScore);
				}
			}
		}
		Map<String, Float> sentiment = new HashMap<>();
		sentiment.put(retrospectiveItem, retrospectiveItemScore);
		System.out.println(sentiment);
		
		return itemsRepository.findAllByRetroId(id);
	}
}
