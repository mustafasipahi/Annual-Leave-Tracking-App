package com.exception;

import static com.constant.ErrorCodes.AVAILABLE_NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class AnnualLeaveException extends BaseMyException {

    public AnnualLeaveException() {
        super(AVAILABLE_NOT_FOUND, BAD_REQUEST, "annual.leave.not.found");
    }
}
