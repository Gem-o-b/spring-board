package com.sparta.board.exception;

import com.sparta.board.entity.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    private final ExceptionEnum exceptionEnum;


}
