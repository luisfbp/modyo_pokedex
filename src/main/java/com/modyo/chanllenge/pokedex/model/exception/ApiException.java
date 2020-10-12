package com.modyo.chanllenge.pokedex.model.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiException {

    private HttpStatus status;
    private transient LocalDateTime timestamp;
    private String message;
    private String debugMessage;

    public ApiException(HttpStatus status, String message) {
        timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }

    public ApiException(HttpStatus status, String message, Throwable ex) {
        timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

}
