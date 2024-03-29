package com.retrospective.repositories;

import com.retrospective.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//JPA repo for retrospective items
public interface ItemsRepository extends JpaRepository<Item, Long> {
	List<Item> findAllByRetroId(Long id);
}
