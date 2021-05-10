package com.retrospective.repositories;

import com.retrospective.models.SentimentAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentimentAnalysisRepository extends JpaRepository<SentimentAnalysis, Long> {
}
