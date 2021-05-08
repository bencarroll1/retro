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
//setting profile to be local so when the API is ran with this
//profile selected, it will load this data
@Profile("local")
public class LoadDatabase {
	
	@Bean
	public CommandLineRunner initDatabase(RetrosRepository retrosRepository, ItemsRepository itemsRepository, ActionItemsRepository actionItemsRepository) {
		return args -> {
			log.info("Beginning preload of data");
			
			//creating data for first sample retrospective
			Retro retro1 = retrosRepository.save(new Retro("Software Development Team Retrospective", false));
			
			//adding items
			List<Item> itemsForRetro1 = new ArrayList<>();
			itemsForRetro1.add(new Item("Excellent communication with team. Happy with how this sprint went", GOOD, false, 0, retro1));
			itemsForRetro1.add(new Item("Frustrated that I was blocked by database problem. Could not do my work", BAD, false, 2, retro1));
			itemsForRetro1.add(new Item("How can we solve the database issue?", QUESTION, false, -1, retro1));
			itemsRepository.saveAll(itemsForRetro1);
			
			//adding action items
			List<ActionItem> actionItemsForRetro1 = new ArrayList<>();
			actionItemsForRetro1.add(new ActionItem("Retro 1 - Action Item Number 1", retro1));
			actionItemsForRetro1.add(new ActionItem("Retro 1 - Action Item Number 2", retro1));
			actionItemsForRetro1.add(new ActionItem("Retro 1 - Action Item Number 3", retro1));
			actionItemsRepository.saveAll(actionItemsForRetro1);
			
			//creating data for second sample retrospective
			Retro retro2 = retrosRepository.save(new Retro("Devops Team Retrospective", true));
			
			List<Item> itemsForRetro2 = new ArrayList<>();
			itemsForRetro2.add(new Item("Hated the amount of meetings we had this sprint. Little time for actual development", BAD, false, -4, retro2));
			itemsForRetro2.add(new Item("Can we try to limit the number of meetings we have in a week?", QUESTION, false, -1, retro2));
			itemsForRetro2.add(new Item("Thorough discussion on design of our new product. Excited as this will give us the ability to quickly proceed with development", GOOD, false, 3, retro2));
			itemsRepository.saveAll(itemsForRetro2);
			
			List<ActionItem> actionItemsForRetro2 = new ArrayList<>();
			actionItemsForRetro2.add(new ActionItem("Retro 2 - Action Item Number 1", retro2));
			actionItemsForRetro2.add(new ActionItem("Retro 2 - Action Item Number 2", retro2));
			actionItemsForRetro2.add(new ActionItem("Retro 2 - Action Item Number 3", retro2));
			actionItemsRepository.saveAll(actionItemsForRetro2);
			
			log.info("Finished preload of data");
		};
	}
	
}
