package com.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BaseMyException extends RuntimeException {

    private int code;
    private HttpStatus status;
    private String messageKey;

    public BaseMyException(int code, HttpStatus status, String messageKey) {
        super(messageKey);
        this.code = code;
        this.status = status;
        this.messageKey = messageKey;
    }
}
