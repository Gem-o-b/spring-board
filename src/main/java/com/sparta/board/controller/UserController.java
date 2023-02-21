package com.sparta.board.controller;


import com.sparta.board.dto.UserRequestDto;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "회원", description = "회원 API 입니다.")
public class UserController {

    private final UserService userService;
    @Operation(summary = "회원가입 메서드", description = "회원가입 메서드")
    @PostMapping("/signup")
    public ResponseEntity<Object> userJoin(@RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult) { //BindingResult : @Valid 실패시 에러를 담음
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("형식에 맞지않습니다");
        }
        return userService.addMember(userRequestDto);
    }

    @Operation(summary = "로그인 메서드", description = "로그인 메서드")
    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<Object> userLogin(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response){
        return userService.login(userRequestDto, response);
    }

    @Operation(summary = "회원탈퇴 메서드", description = "회원탈퇴 메서드")
    @PostMapping("/withdraw")
    public ResponseEntity<Object> userWithdraw(@RequestBody UserRequestDto userRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) { //BindingResult : @Valid 실패시 에러를 담음

        return userService.userWithdraw(userRequestDto, userDetails.getUser());
    }

}
