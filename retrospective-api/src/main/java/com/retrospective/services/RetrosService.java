package com.retrospective.services;

import com.retrospective.models.ActionItem;
import com.retrospective.models.Item;
import com.retrospective.models.Retro;
import com.retrospective.repositories.ActionItemsRepository;
import com.retrospective.repositories.ItemsRepository;
import com.retrospective.repositories.RetrosRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetrosService {
	private final RetrosRepository retrosRepository;
	private final ItemsRepository itemsRepository;
	private final ActionItemsRepository actionItemsRepository;
	
	RetrosService(RetrosRepository retrosRepository, ItemsRepository itemsRepository, ActionItemsRepository actionItemsRepository) {
		this.retrosRepository = retrosRepository;
		this.itemsRepository = itemsRepository;
		this.actionItemsRepository = actionItemsRepository;
	}
	
	public List<Retro> getAllRetros() {
		return retrosRepository.findAll();
	}
	
	public Retro addRetro(Retro retro) {
		return retrosRepository.save(retro);
	}
	
	public Retro getRetroById(Long id) throws Exception {
		return retrosRepository.findById(id)
				.orElseThrow(Exception::new);
	}
	
	public List<Item> getRetroItemsById(Long id) {
		return itemsRepository.findAllByRetroId(id);
	}
	
	public List<ActionItem> getRetroActionItemsById(Long id) {
		return actionItemsRepository.findAllByRetroId(id);
	}
	
	public Item addItemToRetro(Long id, Item item) throws Exception {
		Retro retro = getRetroById(id);
		item.setRetro(retro);
		return itemsRepository.save(item);
	}
	
	public ActionItem addActionItemToRetro(Long id, ActionItem actionItem) throws Exception {
		Retro retro = getRetroById(id);
		actionItem.setRetro(retro);
		return actionItemsRepository.save(actionItem);
	}
	
}
