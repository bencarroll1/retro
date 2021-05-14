package com.retrospective.repositories;

import com.retrospective.models.SentimentAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

//JPA repo for retrospective sentiment analysis
public interface SentimentAnalysisRepository extends JpaRepository<SentimentAnalysis, Long> {
}
