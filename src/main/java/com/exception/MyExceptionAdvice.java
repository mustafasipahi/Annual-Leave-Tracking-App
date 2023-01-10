package com.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.constant.ErrorCodes.UNKNOWN_ERROR;

@Slf4j
@ControllerAdvice
public class MyExceptionAdvice {

    @ExceptionHandler(BaseMyException.class)
    public ResponseEntity<ErrorResponse> handleException(BaseMyException e) {
        log.error("Exception occurred!", e);
        return ResponseEntity
            .status(e.getStatus())
            .body(ErrorResponse.of(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("An unknown exception occurred!", e);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.of(UNKNOWN_ERROR, e.getMessage()));
    }
}
