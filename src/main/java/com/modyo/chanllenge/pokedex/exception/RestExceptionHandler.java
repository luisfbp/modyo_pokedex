package com.modyo.chanllenge.pokedex.exception;

import com.modyo.chanllenge.pokedex.model.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiException> apiExceptionHandler (Exception ex) {
        LOGGER.error("Unexpected ERROR ", ex);
        // Default handler Exception
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown Internal Server Error"));
    }

}
