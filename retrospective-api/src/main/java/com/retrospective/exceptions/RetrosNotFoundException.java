package com.retrospective.exceptions;

public class RetrosNotFoundException extends Exception {
	public RetrosNotFoundException() {
		super("Could not find retrospective.");
	}
}
