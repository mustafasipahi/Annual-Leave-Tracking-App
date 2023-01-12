package com.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BaseMyException extends RuntimeException {

    private final int code;
    private final HttpStatus status;
    private final String messageKey;

    public BaseMyException(int code, HttpStatus status, String messageKey) {
        super(messageKey);
        this.code = code;
        this.status = status;
        this.messageKey = messageKey;
    }
}
