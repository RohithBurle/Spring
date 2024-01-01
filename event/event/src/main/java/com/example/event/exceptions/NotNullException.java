package com.example.event.exceptions;

public class NotNullException extends RuntimeException {
	
	public NotNullException(String errorMessage)
	{
		super(errorMessage);
	}
}
