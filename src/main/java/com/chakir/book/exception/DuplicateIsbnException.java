package com.chakir.book.exception;

public class DuplicateIsbnException extends RuntimeException{
	public DuplicateIsbnException(String message) {
		super(message);
	}
}
