package com.questionoverflow.qof.common.exception;

public enum ErrorMessage {
    NO_POSTS("해당 게시글이 없습니다."),
    NO_JWT_TOKEN("토큰이 존재하지 않습니다.");


    private final String desc;

    ErrorMessage(String desc){
        this.desc = desc;
    }

    public String desc(){
        return desc;
    }


}
