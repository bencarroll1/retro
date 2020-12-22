package com.retrospective.repositories;

import com.retrospective.models.ActionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//JPA repo for retrospective action items
public interface ActionItemsRepository extends JpaRepository<ActionItem, Long> {
	List<ActionItem> findAllByRetroId(Long id);
}
