package com.eduardo.MarvelApi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ApiErrorMessage {

    private HttpStatus status;
    private String message;
}
