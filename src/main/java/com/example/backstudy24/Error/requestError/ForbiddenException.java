package com.example.backstudy24.Error.requestError;
import com.example.backstudy24.Error.ErrorCode;

public class ForbiddenException extends BusinessException {

    public ForbiddenException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}