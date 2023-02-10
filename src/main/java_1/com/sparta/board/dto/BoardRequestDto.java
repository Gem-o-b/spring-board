package com.sparta.board.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto {
    private String title;
    private String userName;
    private String content;

    private String password;

}
