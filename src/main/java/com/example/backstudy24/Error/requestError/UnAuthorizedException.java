package com.example.backstudy24.Error.requestError;
import com.example.backstudy24.Error.ErrorCode;

public class UnAuthorizedException  extends BusinessException{

    public UnAuthorizedException(String message, ErrorCode code) {
        super(message, code);
    }
}
