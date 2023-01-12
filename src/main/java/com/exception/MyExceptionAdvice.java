package com.exception;

import com.service.TranslateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import java.util.Locale;
import java.util.Optional;

import static com.constant.ErrorCodes.UNKNOWN_ERROR;

@Slf4j
@AllArgsConstructor
@ControllerAdvice
public class MyExceptionAdvice {

    private final HttpServletRequest request;
    private final TranslateService translateService;

    @ExceptionHandler(BaseMyException.class)
    public ResponseEntity<ErrorResponse> handleException(BaseMyException e) {
        return getMultiLanguageException(e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("An unknown exception occurred!", e);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.of(UNKNOWN_ERROR, e.getMessage()));
    }

    private ResponseEntity<ErrorResponse> getMultiLanguageException(BaseMyException e) {
        final Locale local = Optional.ofNullable(request.getLocale()).orElse(new Locale("tr"));
        final String message = translateService.getMessage(e.getMessageKey(), local);
        log.error("Exception occurred!", new BaseMyException(e.getCode(), e.getStatus(), message));
        return ResponseEntity
            .status(e.getStatus())
            .body(new ErrorResponse(e.getCode(), message));
    }
}
