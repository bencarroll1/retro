package com.retrospective.exceptions;

public class ActionItemNotFoundException extends Exception{
	public ActionItemNotFoundException(){
		super("Could not find action item.");
	}
}
