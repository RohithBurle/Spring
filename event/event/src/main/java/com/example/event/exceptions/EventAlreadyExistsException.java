package com.example.event.exceptions;

public class EventAlreadyExistsException extends RuntimeException {
	
	public EventAlreadyExistsException (String message) {
		super(message);
	}
}
