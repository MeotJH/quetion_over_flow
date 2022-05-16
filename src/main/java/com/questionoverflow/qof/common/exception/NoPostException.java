package com.questionoverflow.qof.common.exception;

public class NoPostException extends IllegalArgumentException{

    public NoPostException(Long id) {
        super(ErrorMessage.NO_POSTS.desc() + id);
    }
}
