package com.example.backstudy24.Error.requestError;
import com.example.backstudy24.Error.ErrorCode;

public class ExpiredRefreshTokenException extends BusinessException {

    public ExpiredRefreshTokenException(String message, ErrorCode code) {
        super(message, code);
    }
}