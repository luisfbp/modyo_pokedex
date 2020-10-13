package com.modyo.chanllenge.pokedex.model.exception;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * DTO to render in the API responses when an error occurs
 */
@Data
public class ApiError {

    private HttpStatus status;
    private transient LocalDateTime timestamp;
    private String message;
    @JsonIgnore
    private String internalMessage;
    private String debugMessage;

    public ApiError(HttpStatus status, String message) {
        timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }

    public ApiError(HttpStatus status, String internalMessage, Throwable ex) {
        timestamp = LocalDateTime.now();
        this.status = status;
        this.internalMessage = internalMessage;
        this.debugMessage = ex.getLocalizedMessage();
    }

}
