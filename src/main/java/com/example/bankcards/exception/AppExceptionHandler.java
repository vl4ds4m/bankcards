package com.example.bankcards.exception;

import com.example.bankcards.openapi.model.InvalidRequestMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler({
            InvalidRequestException.class,
            MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InvalidRequestMessage handle(Exception e) {
        return new InvalidRequestMessage(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public InvalidRequestMessage handle(AuthorizationException e) {
        return new InvalidRequestMessage(e.getMessage());
    }

}
