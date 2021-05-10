package com.retrospective.exceptions;

public class SentimentAnalysisNotFoundException extends Exception {
	public SentimentAnalysisNotFoundException() {
		super("Could not find sentiment analysis for this retrospective.");
	}
}
