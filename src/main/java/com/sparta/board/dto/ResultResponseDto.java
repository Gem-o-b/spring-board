package com.sparta.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter

public class ResultResponseDto {



    private int statusCode;
    private String msg ;

    @Builder
    public ResultResponseDto( String msg,int statusCode) {
        this.msg = msg;
        this.statusCode =  statusCode;

    }
}
