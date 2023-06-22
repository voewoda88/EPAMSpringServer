package com.example.myspring.controllers;

import com.example.myspring.models.ExceptionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.InvalidParameterException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionController {

    private final Logger logger = LoggerFactory.getLogger(ExceptionController.class);
    @ExceptionHandler({ InvalidParameterException.class })
    public ResponseEntity<Object> handleInvalidParameterException(InvalidParameterException ex) {

        var exceptionModel = new ExceptionModel();

        exceptionModel.setMessage(ex.getMessage());
        exceptionModel.setError(HttpStatusCode.valueOf(400));

        logger.error(ex.getMessage());

        return new ResponseEntity<>(exceptionModel, exceptionModel.getError());
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {

        var exceptionModel = new ExceptionModel();

        exceptionModel.setMessage("Exception: " + ex.getMessage());
        exceptionModel.setError(HttpStatusCode.valueOf(500));

        logger.error("Error: " + ex.getMessage());

        return new ResponseEntity<>(exceptionModel, exceptionModel.getError());
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex) {

        ExceptionModel exceptionModel = new ExceptionModel();

        exceptionModel.setMessage(ex.getMessage());
        exceptionModel.setError(HttpStatusCode.valueOf(404));

        logger.error(ex.getMessage());

        return new ResponseEntity<>(exceptionModel, exceptionModel.getError());
    }

    @ExceptionHandler(value = { EmptyResultDataAccessException.class })
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        ExceptionModel exceptionModel = new ExceptionModel();

        exceptionModel.setMessage(ex.getMessage());
        exceptionModel.setError(HttpStatusCode.valueOf(404));

        logger.error(ex.getMessage());

        return new ResponseEntity<>(exceptionModel, exceptionModel.getError());
    }

}

