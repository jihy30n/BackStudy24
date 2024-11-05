package com.example.backstudy24.Error.requestError;
import com.example.backstudy24.Error.ErrorCode;

public class InvalidTokenException extends BusinessException {

    public InvalidTokenException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}