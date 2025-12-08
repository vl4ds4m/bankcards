package com.example.bankcards.exception;

import com.example.bankcards.openapi.model.InvalidRequestMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InvalidRequestMessage handle(InvalidRequestException e) {
        return new InvalidRequestMessage(e.getMessage());
    }

}
