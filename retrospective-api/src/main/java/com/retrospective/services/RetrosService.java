package com.retrospective.services;

import com.retrospective.exceptions.ActionItemNotFoundException;
import com.retrospective.exceptions.ItemNotFoundException;
import com.retrospective.exceptions.RetrosNotFoundException;
import com.retrospective.models.ActionItem;
import com.retrospective.models.Item;
import com.retrospective.models.Retro;
import com.retrospective.models.SentimentAnalysis;
import com.retrospective.repositories.ActionItemsRepository;
import com.retrospective.repositories.ItemsRepository;
import com.retrospective.repositories.RetrosRepository;
import com.retrospective.repositories.SentimentAnalysisRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

//service annotation
//service layer stores business logic
@Service
public class RetrosService {
	//creating an instance of each repo
	private final RetrosRepository retrosRepository;
	private final ItemsRepository itemsRepository;
	private final ActionItemsRepository actionItemsRepository;
	private final SentimentAnalysisRepository sentimentAnalysisRepository;
	
	RetrosService(RetrosRepository retrosRepository, ItemsRepository itemsRepository, ActionItemsRepository actionItemsRepository, SentimentAnalysisRepository sentimentAnalysisRepository) {
		this.retrosRepository = retrosRepository;
		this.itemsRepository = itemsRepository;
		this.actionItemsRepository = actionItemsRepository;
		this.sentimentAnalysisRepository = sentimentAnalysisRepository;
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
	
	public SentimentAnalysis getRetroItemsByIdForSentimentAnalysis(Long id) throws IOException {
		Retro retro = new Retro();
		SentimentAnalysis sentimentAnalysis = new SentimentAnalysis();
		retro.setId(id);
		
		float retrospectiveItemScore = 0;
		
		ArrayList<String> stopWords = new ArrayList<>();
		BufferedReader stopWordsBuffReader = new BufferedReader(new FileReader("src/main/resources/Dictionaries/stopWords.txt"));
		String line;
		while ((line = stopWordsBuffReader.readLine()) != null) {
			stopWords.add(line);
		}
		stopWordsBuffReader.close();
		
		Map<String, String> AfinnDictionary = new HashMap<>();
		BufferedReader AfinnBuffReader = new BufferedReader(new FileReader("src/main/resources/Dictionaries/AFINN-en-165.txt"));
		while ((line = AfinnBuffReader.readLine()) != null) {
			String[] parts = line.split("\t");
			AfinnDictionary.put(parts[0], parts[1]);
		}
		AfinnBuffReader.close();
		
		String retroItemWords = (itemsRepository.findAllByRetroId(id).toString().replaceAll("[^a-zA-Z]", " ")).trim();
		String[] words = retroItemWords.split(" ");
		
		for (String word : words) {
			if (stopWords.contains(word.toLowerCase())) {
				// skip word
			} else {
				if (AfinnDictionary.get(word) != null) {
//					System.out.println(word);
					String wordScore = AfinnDictionary.get(word.toLowerCase());
//					System.out.println("Current retrospective sentiment analysis score " + retrospectiveItemScore);
					retrospectiveItemScore = retrospectiveItemScore + Integer.parseInt(wordScore);
					sentimentAnalysis.setScore(retrospectiveItemScore);
//					System.out.println("New retrospective score: " + retrospectiveItemScore);
				}
			}
		}
		sentimentAnalysis.setRetro(retro);
		sentimentAnalysis.setId(retro.getId());
		sentimentAnalysis.setRetroItems(retroItemWords);
//		System.out.println(sentimentAnalysis.getScore());
		
		return sentimentAnalysis;
	}
	
	public List<Item> getRetroItemsAndExportToCSV(Long id, HttpServletResponse response) throws IOException, RetrosNotFoundException {
		response.setContentType("text/csv");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String currentDateTime = dateFormatter.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=retrospective" +
				"_" +
				this.getRetroById(id).getName() +
				"_items_ID-" +
				id +
				"_" +
				currentDateTime +
				".csv";
		response.setHeader(headerKey, headerValue);
		
		List<Item> listItems = this.getRetroItemsById(id);
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		String[] csvHeader = {"Item ID", "Created", "Description", "Type", "Votes"};
		String[] nameMapping = {"id", "created", "description", "type", "itemVotes"};
		
		csvWriter.writeHeader(csvHeader);
		
		for (Item item : listItems) {
			csvWriter.write(item, nameMapping);
		}
		csvWriter.close();
		
		return itemsRepository.findAllByRetroId(id);
	}
	
	public List<ActionItem> getRetroActionItemsAndExportToCSV(Long id, HttpServletResponse response) throws IOException, RetrosNotFoundException {
		response.setContentType("text/csv");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String currentDateTime = dateFormatter.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=retrospective" +
				"_" +
				this.getRetroById(id).getName() +
				"_actionItems_ID-" +
				id +
				"_" +
				currentDateTime +
				".csv";
		response.setHeader(headerKey, headerValue);
		
		List<ActionItem> listActionItems = this.getRetroActionItemsById(id);
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		String[] csvHeader = {"Action Item ID", "Created", "Description"};
		String[] nameMapping = {"id", "created", "description"};
		
		csvWriter.writeHeader(csvHeader);
		
		for (ActionItem actionItem : listActionItems) {
			csvWriter.write(actionItem, nameMapping);
		}
		csvWriter.close();
		
		return actionItemsRepository.findAllByRetroId(id);
	}
}
