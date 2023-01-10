package com.exception;

import lombok.Getter;

import static com.constant.ErrorCodes.INVALID_PHONE_NUMBER;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
public class PhoneNumberException extends BaseMyException {

    public PhoneNumberException() {
        super(INVALID_PHONE_NUMBER, BAD_REQUEST, "Invalid Phone Number");
    }
}
