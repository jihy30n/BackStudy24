package com.example.backstudy24.Error.requestError;
import com.example.backstudy24.Error.ErrorCode;

public class NotFoundException extends BusinessException {

    public NotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}