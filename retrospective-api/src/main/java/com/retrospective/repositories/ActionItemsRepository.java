package com.retrospective.repositories;

import com.retrospective.models.ActionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionItemsRepository extends JpaRepository<ActionItem, Long> {
	List<ActionItem> findAllByRetroId(Long id);
}
