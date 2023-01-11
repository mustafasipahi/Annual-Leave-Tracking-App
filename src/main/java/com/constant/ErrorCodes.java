package com.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorCodes {

    public static final int INVALID_PHONE_NUMBER = 1001;
    public static final int USER_NOT_FOUND = 1002;
    public static final int INVALID_RULE = 1003;
    public static final int NOT_AVAILABLE_DAYS = 1004;
    public static final int AVAILABLE_NOT_FOUND = 1005;

    public static final int UNKNOWN_ERROR = 9999;
}
