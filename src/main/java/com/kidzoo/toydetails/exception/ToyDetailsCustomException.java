package com.kidzoo.toydetails.exception;

import java.io.Serial;

public class ToyDetailsCustomException extends RuntimeException {

	  @Serial
	  private static final long serialVersionUID = 1L;
	  private final String errorCode;
	  private final String statusCode;

	  public ToyDetailsCustomException(String errorCode, String message, String statusCode) {
	    super(message);
	    this.errorCode = errorCode;
	    this.statusCode = statusCode;
	  }

	  public String getErrorCode() {
	    return errorCode;
	  }
	  
	  public String getStatusCode() {
		    return statusCode;
		  }
}
