package com.retrospective.repositories;

import com.retrospective.models.SentimentAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SentimentAnalysisRepository extends JpaRepository<SentimentAnalysis, Long> {
	List<SentimentAnalysis> findSentimentAnalysisByRetroId(Long id);
}
