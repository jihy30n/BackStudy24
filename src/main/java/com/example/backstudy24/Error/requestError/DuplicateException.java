package com.example.backstudy24.Error.requestError;
import com.example.backstudy24.Error.ErrorCode;

public class DuplicateException extends BusinessException {

    public DuplicateException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}