package com.sparta.board.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {


    private String msg ;
    private int statusCode;


    public UserResponseDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode =  statusCode;

    }
}
