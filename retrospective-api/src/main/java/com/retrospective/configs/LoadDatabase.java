package com.retrospective.configs;

import com.retrospective.models.ActionItem;
import com.retrospective.models.Item;
import com.retrospective.models.Retro;
import com.retrospective.repositories.ActionItemsRepository;
import com.retrospective.repositories.ItemsRepository;
import com.retrospective.repositories.RetrosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

import static com.retrospective.models.ItemType.*;

@Configuration
@Slf4j
@Profile("local")
public class LoadDatabase {
	
	@Bean
	public CommandLineRunner initDatabase(RetrosRepository retrosRepository, ItemsRepository itemsRepository, ActionItemsRepository actionItemsRepository) {
		return args -> {
			log.info("Beginning preload of data");
			
			Retro retro1 = retrosRepository.save(new Retro("First Retrospective", false));
			
			List<Item> itemsForRetro1 = new ArrayList<>();
			itemsForRetro1.add(new Item("Retro 1 :: Item Number 1", GOOD, false, 0, retro1));
			itemsForRetro1.add(new Item("Retro 1 :: Item Number 2", BAD, false, 2, retro1));
			itemsForRetro1.add(new Item("Retro 1 :: Item Number 3", QUESTION, false, -1, retro1));
			itemsRepository.saveAll(itemsForRetro1);
			
			List<ActionItem> actionItemsForRetro1 = new ArrayList<>();
			actionItemsForRetro1.add(new ActionItem("Retro 1 :: Action Item Number 1", retro1));
			actionItemsForRetro1.add(new ActionItem("Retro 1 :: Action Item Number 2", retro1));
			actionItemsForRetro1.add(new ActionItem("Retro 1 :: Action Item Number 3", retro1));
			actionItemsRepository.saveAll(actionItemsForRetro1);
			
			
			Retro retro2 = retrosRepository.save(new Retro("Second Retrospective", true));
			
			List<Item> itemsForRetro2 = new ArrayList<>();
			itemsForRetro2.add(new Item("Retro 2 :: Item Number 1", BAD, false, -4, retro2));
			itemsForRetro2.add(new Item("Retro 2 :: Item Number 2", QUESTION, false, -1, retro2));
			itemsForRetro2.add(new Item("Retro 2 :: Item Number 3", GOOD, false, 3, retro2));
			itemsRepository.saveAll(itemsForRetro2);
			
			List<ActionItem> actionItemsForRetro2 = new ArrayList<>();
			actionItemsForRetro2.add(new ActionItem("Retro 2 :: Action Item Number 1", retro2));
			actionItemsForRetro2.add(new ActionItem("Retro 2 :: Action Item Number 2", retro2));
			actionItemsForRetro2.add(new ActionItem("Retro 2 :: Action Item Number 3", retro2));
			actionItemsRepository.saveAll(actionItemsForRetro2);
			
			log.info("Finished preload of data");
		};
	}
	
}
