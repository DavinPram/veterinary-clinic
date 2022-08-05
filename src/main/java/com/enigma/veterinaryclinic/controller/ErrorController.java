package com.enigma.veterinaryclinic.controller;

import com.enigma.veterinaryclinic.exception.BadRequestException;
import com.enigma.veterinaryclinic.exception.MethodNotAllowedException;
import com.enigma.veterinaryclinic.exception.NotFoundException;
import com.enigma.veterinaryclinic.response.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception){
        HttpStatus status = HttpStatus.NOT_FOUND;
        WebResponse<String> response = new WebResponse<>(
                exception.getMessage(),
                null
        );
        return new ResponseEntity<>(response,status);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(BadRequestException exception){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        WebResponse<String> response = new WebResponse<>(
                exception.getMessage(),
                null
        );
        return new ResponseEntity<>(response,status);
    }

    @ExceptionHandler(value = {MethodNotAllowedException.class})
    public ResponseEntity<Object> handleBadRequestException(MethodNotAllowedException exception){
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        WebResponse<String> response = new WebResponse<>(
                exception.getMessage(),
                null
        );
        return new ResponseEntity<>(response,status);
    }
}
