package com.exception;

import static com.constant.ErrorCodes.NOT_AVAILABLE_DAYS;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class AvailableDaysException extends BaseMyException {

    public AvailableDaysException() {
        super(NOT_AVAILABLE_DAYS, BAD_REQUEST, "not.available.days");
    }
}
