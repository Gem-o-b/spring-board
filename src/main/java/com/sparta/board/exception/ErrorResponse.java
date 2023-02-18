package com.sparta.board.exception;

import com.sparta.board.entity.ExceptionEnum;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ErrorResponse {
    private final int statusCode;
    private final String msg;


    public static ResponseEntity<ErrorResponse> toResponseEntity(ExceptionEnum exceptionEnum){

        return ResponseEntity.status(exceptionEnum.getCode()).body(ErrorResponse.builder().msg(exceptionEnum.getMsg()).statusCode(exceptionEnum.getCode()).build());
    }
}
