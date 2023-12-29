package com.kidzoo.toydetails.exception;


import lombok.extern.slf4j.Slf4j;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class ToyDetailsErrorHandler {
	private static final String serialVersionUID = "5770151f-3f2c-42e1-9b1a-0f82af74210c2";
	@ExceptionHandler
	public ResponseEntity<Error> handleException(final ToyDetailsCustomException err) {
		Error error;
		error = new Error(Integer.valueOf(err.getErrorCode()), err.getStatusCode(), err.getMessage());
		error.setReferenceId(serialVersionUID);
		return ResponseEntity.status(Integer.valueOf(err.getErrorCode())).body(error);
	}

	@ExceptionHandler
	public ResponseEntity<Error> handleException(final ServletRequestBindingException err) {
		Error error = new Error(HttpStatus.BAD_REQUEST.value(),
				"TOY_DETAILS_MISSING_MANDATORY_INPUT", err.getLocalizedMessage());
		error.setReferenceId(serialVersionUID);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
	}

	@ExceptionHandler(value
			= { IllegalArgumentException.class, IllegalStateException.class })
	protected ResponseEntity<Object> handleConflict(
			RuntimeException ex, WebRequest request) {
		Error error = new Error(HttpStatus.BAD_REQUEST.value(),
				"TOY_DETAILS_MISSING_MANDATORY_INPUT", ex.getLocalizedMessage());
		error.setReferenceId(serialVersionUID);
		return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(error);
	}


	private Error errorResponse(final RestClientResponseException err) {
		JSONParser parser = new JSONParser();
		JSONObject jsonError = new JSONObject();
		try {
			jsonError = (JSONObject) parser.parse(err.getResponseBodyAsString());
		} catch (org.json.simple.parser.ParseException pe) {
			log.error("Exception while parsing json object: " + pe);
		}

		Error error = new Error(err.getRawStatusCode(), "error_toy-details-generic-error",
				(String) jsonError.get("errorMessage"));
		error.setReferenceId(serialVersionUID);
		return error;
	}



	private String getErrorMessage(HttpMessageConversionException err) {
		String errorMessage = null;
		if (err.getMessage().contains("NullPointerException")) {
			errorMessage = "Invalid Context Sent in the Request";
		} else if (err.getMessage().contains("Required request body is missing")) {
			errorMessage = "Required request body is missing";
		}
		return errorMessage;
	}

}