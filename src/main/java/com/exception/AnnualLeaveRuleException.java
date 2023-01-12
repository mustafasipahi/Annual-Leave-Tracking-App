package com.exception;

import static com.constant.ErrorCodes.INVALID_RULE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class AnnualLeaveRuleException extends BaseMyException {

    public AnnualLeaveRuleException(String messageKey) {
        super(INVALID_RULE, BAD_REQUEST, messageKey);
    }
}
