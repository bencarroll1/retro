package com.retrospective.repositories;

import com.retrospective.models.Retro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RetrosRepository extends JpaRepository<Retro, Long> {
	Optional<Retro> findById(Long id);
}
