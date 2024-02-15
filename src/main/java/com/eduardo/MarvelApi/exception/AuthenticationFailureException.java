package com.eduardo.MarvelApi.exception;

public class AuthenticationFailureException extends RuntimeException {
    public AuthenticationFailureException(String message) {
        super(message);
    }
}
