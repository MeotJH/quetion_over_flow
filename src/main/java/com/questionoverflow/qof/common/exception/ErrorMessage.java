package com.questionoverflow.qof.common.exception;

public enum ErrorMessage {
    NO_POSTS("해당 게시글이 없습니다.");


    private final String desc;

    ErrorMessage(String desc){
        this.desc = desc;
    }

    public String desc(){
        return desc;
    }


}
