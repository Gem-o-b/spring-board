package com.sparta.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter

public class ResultResponseDto {



    private int statusCode;
    private String msg ;

    @Builder
    private ResultResponseDto( String msg,int statusCode) {
        this.msg = msg;
        this.statusCode =  statusCode;

    }

    public static ResultResponseDto from(String msg,int statusCode){
        return ResultResponseDto.builder()
                .msg(msg)
                .statusCode(statusCode)
                .build();
    }

}
