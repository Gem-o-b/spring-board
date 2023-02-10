package com.sparta.board.controller;


import com.sparta.board.dto.MemberRequestDto;
import com.sparta.board.dto.MemberResponseDto;
import com.sparta.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Object> memberJoin(@RequestBody @Valid MemberRequestDto memberRequestDto, BindingResult bindingResult) { //BindingResult : @Valid 실패시 에러를 담음
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("형식에 맞지않습니다");
        }

        return memberService.addMember(memberRequestDto);
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<Object> memberLogin(@RequestBody MemberRequestDto memberRequestDto,  HttpServletResponse response){


        return memberService.login(memberRequestDto, response);

    }


}
