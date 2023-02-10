package com.sparta.board.controller;


import com.sparta.board.dto.MemberRequestDto;
import com.sparta.board.dto.MemberResponseDto;
import com.sparta.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("api/join")
    public ResponseEntity<Object> memberJoin(@RequestBody @Valid MemberRequestDto memberRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("형식에 맞지않습니다");
        }

        return memberService.addMember(memberRequestDto);
    }

}
