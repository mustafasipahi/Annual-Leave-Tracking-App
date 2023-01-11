package com.exception;

import com.holder.AppContextHolder;
import com.service.TranslateService;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Locale;
import java.util.Optional;

@Getter
public class BaseMyException extends RuntimeException {

    private static final TranslateService translateService;

    private final int code;
    private final HttpStatus status;

    static {
        translateService = AppContextHolder.getBean(TranslateService.class);
    }

    public BaseMyException(int code, HttpStatus status, String messageKey, Locale locale) {
        super(localMessage(messageKey, locale));
        this.code = code;
        this.status = status;
    }

    private static String localMessage(String messageKey, Locale local) {
        final Locale newLocal = Optional.ofNullable(local)
            .orElse(new Locale("tr"));
        return translateService.getMessage(messageKey, newLocal);
    }
}
