package com.eduardo.MarvelApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(HQAlreadyRegistered.class)
    public ResponseEntity<ApiErrorMessage> hqAlreadyRegistered(HQAlreadyRegistered exception){
        ApiErrorMessage threatResponse = new ApiErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorMessage> resourceNotFound(ResourceNotFoundException exception){
        ApiErrorMessage threatResponse = new ApiErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(AuthenticationFailureException.class)
    public ResponseEntity<ApiErrorMessage> authenticationFailure(AuthenticationFailureException exception){
        ApiErrorMessage threatResponse = new ApiErrorMessage(HttpStatus.UNAUTHORIZED, exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(threatResponse);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiErrorMessage> userAlreadyExists(UserAlreadyExistsException exception){
        ApiErrorMessage threatResponse = new ApiErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }
}
