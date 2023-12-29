package com.kidzoo.toydetails.exception;

import com.kidzoo.toydetails.dao.GeneralResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<GeneralResponse> handleUserNotFoundException(UserNotFoundException exception) {

        return new ResponseEntity<>(new GeneralResponse(false,"Operation cannot be completed.  User does not exist."), jsonHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserAlreadyExistException.class)
    public ResponseEntity<GeneralResponse> handleUserExistException(UserAlreadyExistException exception) {
        return new ResponseEntity<>(new GeneralResponse(false,"Operation not allowed. User already exists."), jsonHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }

    HttpHeaders jsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
