package com.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseMyException extends RuntimeException {

    private final int code;
    private final HttpStatus status;

    public BaseMyException(int code, HttpStatus status, String message) {
        super(message);
        this.code = code;
        this.status = status;
    }
}
