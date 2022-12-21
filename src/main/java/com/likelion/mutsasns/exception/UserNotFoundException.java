package com.likelion.mutsasns.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private ErrorCode errorCode;
    private String message;

    public UserNotFoundException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message + "는 이미 있습니다.";
    }
}
