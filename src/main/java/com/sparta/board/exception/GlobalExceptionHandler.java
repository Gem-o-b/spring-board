package com.sparta.board.exception;


import com.sparta.board.dto.ResultResponseDto;
import com.sparta.board.entity.ExceptionEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> customException(CustomException e){

        return ErrorResponse.toResponseEntity(e.getExceptionEnum());

    }

}
