package com.example.backstudy24.Error.requestError;
import com.example.backstudy24.Error.ErrorCode;

public class JwtException extends BusinessException {

    public JwtException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}