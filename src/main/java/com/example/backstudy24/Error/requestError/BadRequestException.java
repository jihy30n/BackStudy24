package com.example.backstudy24.Error.requestError;

import com.example.backstudy24.Error.ErrorCode;

public class BadRequestException extends BusinessException {

    public BadRequestException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}