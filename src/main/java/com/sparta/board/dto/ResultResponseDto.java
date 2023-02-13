package com.sparta.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResultResponseDto {


    private String msg ;
    private int statusCode;


    public ResultResponseDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode =  statusCode;

    }
}
