package com.example.event.exceptions;

public class EventNotExistsException extends RuntimeException{

	public EventNotExistsException(String message)
	{
		super(message);
	}
}
