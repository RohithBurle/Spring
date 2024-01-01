package com.example.event.exceptions;

public class NotEmptyException extends RuntimeException {

	public NotEmptyException(String errorMessage)
	{
		super(errorMessage);
	}
}
