package com.partymanager.handlers;

import com.partymanager.exceptions.ResourceNotFoundException;
import com.partymanager.handlers.dtos.ErrorMessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ResourceNotFoundException.class})
    public ErrorMessageDTO resourceNotFoundExceptionHandler(RuntimeException exception, WebRequest request) {
        return new ErrorMessageDTO(HttpStatus.NOT_FOUND.value(), exception.getMessage(), request.getDescription(false), LocalDateTime.now());
    }
}
