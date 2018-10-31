package com.ayu.CheckOutCounter.service.exception;

@SuppressWarnings("serial")
public class InvalidProductException extends Exception {
	public InvalidProductException(String message) {
		super(message);
	}
}
