package com.exception;

import static com.constant.ErrorCodes.USER_NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class UserNotFoundException extends BaseMyException {

    public UserNotFoundException() {
        super(USER_NOT_FOUND, BAD_REQUEST, "user.not.found");
    }
}
