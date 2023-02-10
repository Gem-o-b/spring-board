package com.sparta.board.service;


import com.sparta.board.dto.MemberRequestDto;
import com.sparta.board.dto.MemberResponseDto;
import com.sparta.board.entity.Member;
import com.sparta.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public ResponseEntity<Object> addMember(MemberRequestDto memberRequestDto) {
        Member member = new Member(memberRequestDto);
        Optional<Member> rst = memberRepository.findByUsername(memberRequestDto.getUsername());

        if (rst.isPresent()){
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        memberRepository.save(member);
        return ResponseEntity.status(HttpStatus.OK).body(new MemberResponseDto("회원가입 성공",HttpStatus.OK.value()));

    }
}
