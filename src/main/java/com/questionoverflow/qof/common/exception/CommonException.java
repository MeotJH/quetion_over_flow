package com.questionoverflow.qof.common.exception;

public class CommonException extends IllegalArgumentException{

    public CommonException(ErrorMessage errorMessage) {
        super(errorMessage.desc());
    }
}
