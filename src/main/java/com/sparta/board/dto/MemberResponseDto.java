package com.sparta.board.dto;

import com.sparta.board.entity.Member;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MemberResponseDto {


    private String msg ;
    private int statusCode;


    public MemberResponseDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode =  statusCode;

    }
}
