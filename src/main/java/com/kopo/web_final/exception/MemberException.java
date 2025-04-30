package com.kopo.web_final.exception;

import com.kopo.web_final.type.ErrorType;

public class MemberException extends Exception{
    private ErrorType errorType;

    public MemberException(ErrorType errorType) {
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }
}
