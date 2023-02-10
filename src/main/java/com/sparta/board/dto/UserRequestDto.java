package com.sparta.board.dto;


import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter

public class UserRequestDto {

    @NotBlank
    @Size(min = 4, max = 10)
    @Pattern(regexp="^(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{4,10}$")
    private String username;

    @NotBlank
    @Size(min = 8, max = 15)
    @Pattern(regexp="^(?=.*[A-za-z])(?=.*\\d)[A-Za-z\\d]{8,15}$")
    private String password;


}
