package com.modyo.chanllenge.pokedex.exception;

import com.modyo.chanllenge.pokedex.model.exception.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    /**
     * Default handler Exception
     * @param ex Exception threw it
     * @return {@link ApiError} to be serialized
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> apiExceptionHandler (Exception ex) {
        LOGGER.error("Unexpected ERROR ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown Internal Server Error"));
    }

    /**
     * Handles custom runtime exceptions {@link CustomApiThrowable}
     * @param ex Exception threw it
     * @return {@link ApiError} to be serialized
     */
    @ExceptionHandler(CustomApiThrowable.class)
    public ResponseEntity<ApiError> apiCustomExceptionHandler (CustomApiThrowable ex) {
        LOGGER.error("Custom Exception threw, message: {}", ex.getApiError().getInternalMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getApiError());
    }

}
