package com.maplecheater.controller;

import com.maplecheater.domain.dto.response.ErrorResponseData;
import com.maplecheater.exception.AuthenticationFailedException;
import com.maplecheater.exception.InvalidVerificationException;
import com.maplecheater.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidVerificationException.class)
    public ErrorResponseData handleInvalidVerificationException(InvalidVerificationException exception) {
        return new ErrorResponseData(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthenticationFailedException.class)
    public ErrorResponseData handleAuthenticationFailedException(AuthenticationFailedException exception) {
        return new ErrorResponseData(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponseData handleUserNotFoundException(UserNotFoundException exception) {
        return new ErrorResponseData(exception.getMessage());
    }
}
