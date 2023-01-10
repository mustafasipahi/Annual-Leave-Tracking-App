package com.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private Date date;
    private int code;
    private String message;

    private ErrorResponse(int code, String message) {
        this.date = new Date();
        this.code = code;
        this.message = message;
    }

    public static ErrorResponse of(BaseMyException exception) {
        return new ErrorResponse(exception.getCode(), exception.getMessage());
    }

    public static ErrorResponse of(int code, String message) {
        return new ErrorResponse(code, message);
    }
}
