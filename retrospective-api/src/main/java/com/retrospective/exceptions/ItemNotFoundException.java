package com.retrospective.exceptions;

public class ItemNotFoundException extends Exception{
	public ItemNotFoundException(){
		super("Could not find item.");
	}
}
