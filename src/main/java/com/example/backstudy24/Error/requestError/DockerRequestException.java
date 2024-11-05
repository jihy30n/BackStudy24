package com.example.backstudy24.Error.requestError;

import com.example.backstudy24.Error.ErrorCode;


public class DockerRequestException extends BusinessException {
    public DockerRequestException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}
