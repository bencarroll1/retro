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
	
	// method to perform a sentiment analysis on a retrospective's items by retro id
	public SentimentAnalysis getRetroItemsByIdForSentimentAnalysis(Long id) throws IOException {
		// creating instance of retro and sentiment analysis objects
		Retro retro = new Retro();
		SentimentAnalysis sentimentAnalysis = new SentimentAnalysis();
		// setting retro id to that given from path variable
		retro.setId(id);
		
		float retrospectiveItemScore = 0;
		String wordLine;
		
		// reading in all stop words using buffered reader and adding them to an arraylist
		ArrayList<String> stopWords = new ArrayList<>();
		BufferedReader stopWordsBuffReader = new BufferedReader(new FileReader("src/main/resources/Dictionaries/stopWords.txt"));
		
		while ((wordLine = stopWordsBuffReader.readLine()) != null) {
			stopWords.add(wordLine);
		}
		stopWordsBuffReader.close();
		
		// reading in all AFINN words and their scores using buffered reader and adding them to a hashmap
		Map<String, String> AfinnDictionary = new HashMap<>();
		BufferedReader AfinnBuffReader = new BufferedReader(new FileReader("src/main/resources/Dictionaries/AFINN-en-165.txt"));
		while ((wordLine = AfinnBuffReader.readLine()) != null) {
			String[] parts = wordLine.split("\t");
			AfinnDictionary.put(parts[0], parts[1]);
		}
		AfinnBuffReader.close();
		
		// getting retrospective item words, coverting object to string, removing anything that is not
		// and alphabetical char, and trimming white space
		String retroItemWords = (itemsRepository.findAllByRetroId(id).toString().replaceAll("[^a-zA-Z]", " ")).trim();
		// splitting retro item words by white space and adding them to array
		String[] words = retroItemWords.split(" ");
		
		// for loop that compares each words in retroItemWords against the stop words,
		// if word is present in stop words array, its skipped
		
		// if word is not present amongst stop words, its sentiment score is gotten from afinn dictionary and retro sentiment score
		// is updated
		// this continues until there are no more words left to check in retroItemWords
		for (String word : words) {
			if (stopWords.contains(word.toLowerCase())) {
				// skip word
				System.out.println("Word skipped: " + word);
			} else {
				if (AfinnDictionary.get(word) != null) {
					String wordScore = AfinnDictionary.get(word.toLowerCase());
					// continuously amending and setting sentiment score
					retrospectiveItemScore = retrospectiveItemScore + Integer.parseInt(wordScore);
					sentimentAnalysis.setScore(retrospectiveItemScore);
				}
			}
		}
		// when overall sentiment score determined, associated retro, retro id and retro words string is added to sentimentAnalysis object
		sentimentAnalysis.setRetro(retro);
		sentimentAnalysis.setId(retro.getId());
		sentimentAnalysis.setRetroItems(retroItemWords);
		
		return sentimentAnalysis;
	}
	
	public List<Item> getRetroItemsAndExportToCSV(Long id, HttpServletResponse response) throws IOException, RetrosNotFoundException {
		// setting response file type to be CSV file
		response.setContentType("text/csv");
		// getting date for outputted file name
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String currentDateTime = dateFormatter.format(new Date());
		
		String headerKey = "Content-Disposition";
		// setting response file name which includes retro name, id, and currentDateTime
		String headerValue = "attachment; filename=retrospective_" +
				this.getRetroById(id).getName() +
				"_items_ID-" + id + "_" + currentDateTime + ".csv";
		
		response.setHeader(headerKey, headerValue);
		
		List<Item> listItems = this.getRetroItemsById(id);
		// pairing headings to item vars and mapping to csv
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
