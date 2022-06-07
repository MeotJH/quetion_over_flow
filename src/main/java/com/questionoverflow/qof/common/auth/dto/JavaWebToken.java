package com.questionoverflow.qof.common.auth.dto;

import lombok.Getter;

@Getter
public class JavaWebToken {

    private String value;

    public JavaWebToken( String value ){
        this.value = value;
    }


}
