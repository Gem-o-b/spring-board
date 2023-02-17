package com.sparta.board.dto;


import lombok.Getter;

import javax.validation.constraints.*;

@Getter

public class UserRequestDto {

    @NotBlank
    @Size(min = 4, max = 10)
    @Pattern(regexp="^(?=.*[a-z])(?=.*\\d)[a-z\\d]{4,10}$",message = "4~10자리 영문대소문자, 숫자로 입력해주세요")
    private String username;

    @NotBlank
    @Size(min = 8, max = 15)
    @Pattern(regexp="^[a-zA-Z0-9~!@#$%^&*()_+=?,./<>{}\\\\[\\\\]\\\\-]{8,15}$",message = "8~15자리 영문대소문자, 숫자로 입력해주세요")
    private String password;


    private boolean isadmin;


}
