package com.kidzoo.toydetails.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Error
{
	private String errorCode;
	private String errorMessage;
	private String referenceId = "5770151f-3f2c-42e1-9b1a-0f82af74210c";
	@JsonIgnore
	private int statusCode;
	
	public Error(int statusCode, String errorCode, String errorMessage)
	{
		setStatusCode(statusCode);
		setErrorCode(errorCode);
		setErrorMessage(errorMessage);
		setReferenceId(referenceId);
	}

}

