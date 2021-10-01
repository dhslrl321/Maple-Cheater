package com.maplecheater.controller;

import com.maplecheater.domain.dto.response.ErrorResponseData;
import com.maplecheater.exception.*;
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
    public ErrorResponseData handleInvalidVerificationException(InvalidVerificationException e) {
        return new ErrorResponseData(e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ErrorResponseData handleUnauthorizedException(UnauthorizedException e) {
        return new ErrorResponseData(e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthenticationFailedException.class)
    public ErrorResponseData handleAuthenticationFailedException(AuthenticationFailedException e) {
        return new ErrorResponseData(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponseData handleUserNotFoundException(UserNotFoundException e) {
        return new ErrorResponseData(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserExistsException.class)
    public ErrorResponseData handleUserExistsException(UserExistsException e) {
        return new ErrorResponseData(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(VerificationNotFoundException.class)
    public ErrorResponseData handleVerificationNotFoundException(VerificationNotFoundException e) {
        return new ErrorResponseData(e.getMessage());
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @ExceptionHandler(CheaterNotFoundException.class)
    public ErrorResponseData handleCheaterNotFoundException(CheaterNotFoundException e) {
        return new ErrorResponseData(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalDataException.class)
    public ErrorResponseData handleIllegalDataException(IllegalDataException e) {
        return new ErrorResponseData(e.getMessage());
    }
}
