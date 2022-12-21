package com.likelion.mutsasns.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserNotFoundException extends RuntimeException {
    private ErrorCode errorCode;
    private String message;
}
