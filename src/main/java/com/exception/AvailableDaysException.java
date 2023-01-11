package com.exception;

import java.util.Locale;

import static com.constant.ErrorCodes.NOT_AVAILABLE_DAYS;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class AvailableDaysException extends BaseMyException {

    public AvailableDaysException(Locale locale) {
        super(NOT_AVAILABLE_DAYS, BAD_REQUEST, "not.available.days", locale);
    }
}
