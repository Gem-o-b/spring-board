package com.sparta.board.controller;


import com.sparta.board.dto.UserRequestDto;
import com.sparta.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Object> userJoin(@RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult) { //BindingResult : @Valid 실패시 에러를 담음
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("형식에 맞지않습니다");
        }

        return userService.addMember(userRequestDto);
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<Object> userLogin(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response){


        return userService.login(userRequestDto, response);

    }


}
