package com.modyo.chanllenge.pokedex.exception;

import com.modyo.chanllenge.pokedex.model.exception.ApiError;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;

/**
 * Let throw custom errors
 */
public class CustomApiThrowable extends NestedRuntimeException {

    private final ApiError apiError;

    public CustomApiThrowable(String internalMessage, Throwable error) {
        super(internalMessage, error);
        this.apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, internalMessage, error);
    }

    public ApiError getApiError() {
        return this.apiError;
    }

}
