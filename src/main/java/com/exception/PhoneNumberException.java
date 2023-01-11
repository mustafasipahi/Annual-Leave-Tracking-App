package com.exception;

import lombok.Getter;

import java.util.Locale;

import static com.constant.ErrorCodes.INVALID_PHONE_NUMBER;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
public class PhoneNumberException extends BaseMyException {

    public PhoneNumberException(Locale locale) {
        super(INVALID_PHONE_NUMBER, BAD_REQUEST, "phone.number.invalid", locale);
    }
}
