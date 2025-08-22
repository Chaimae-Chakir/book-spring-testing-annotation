package com.chakir.book.web;

import com.chakir.book.exception.DuplicateIsbnException;
import com.chakir.book.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorHandling extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleNotFound(RuntimeException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
	}

	@ExceptionHandler(DuplicateIsbnException.class)
	public ResponseEntity<Object> handleConflict(RuntimeException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", ex.getMessage()));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
				HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = error instanceof FieldError fe ? fe.getField() : error.getObjectName();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		return ResponseEntity.badRequest().body(Map.of("errors", errors));
	}
}


