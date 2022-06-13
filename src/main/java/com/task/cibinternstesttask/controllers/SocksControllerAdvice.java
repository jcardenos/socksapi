package com.task.cibinternstesttask.controllers;

import com.task.cibinternstesttask.exceptions.SocksException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SocksControllerAdvice {
    @ExceptionHandler(SocksException.class)
    public ResponseEntity errorHandler(SocksException exception) {
        return new ResponseEntity<>(exception.getError().getHttpStatus());
    }
}