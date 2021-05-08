package com.retrospective.sentiment;

import com.retrospective.repositories.ItemsRepository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SentimentAnalysis {
	private static ItemsRepository itemsRepository;
//	final ItemsRepository itemsRepository;
	
	public SentimentAnalysis(ItemsRepository itemsRepository) {
		SentimentAnalysis.itemsRepository = itemsRepository;
	}
	
	public static void main(String[] args) throws IOException {
		try {
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
//			System.out.println(map.toString());
			
			itemsRepository.findAllByRetroId(1L);
			Scanner inputStream = new Scanner(new FileReader("src/main/resources/Dictionaries/TestRetroItems.csv"));
			
			while (inputStream.hasNextLine()) {
				float retrospectiveItemScore = 0;
				retrospectiveItem = inputStream.nextLine();
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
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
