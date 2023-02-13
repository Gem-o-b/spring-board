package com.sparta.board.service;


import com.sparta.board.dto.UserRequestDto;
import com.sparta.board.dto.UserResponseDto;
import com.sparta.board.entity.Users;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    @Transactional
    public ResponseEntity<Object> addMember(UserRequestDto userRequestDto) {
        Users users = new Users(userRequestDto);
        Optional<Users> rst = userRepository.findByUsername(userRequestDto.getUsername());

        if (rst.isPresent()){
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        userRepository.save(users);
        return ResponseEntity.status(HttpStatus.OK).body(new UserResponseDto("회원가입 성공",HttpStatus.OK.value()));

    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> login(UserRequestDto userRequestDto, HttpServletResponse response) {

        String username = userRequestDto.getUsername();
        String password = userRequestDto.getPassword();

        Users users = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        if(!users.getPassword().equals(password)){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(users.getUsername()));
        return ResponseEntity.status(HttpStatus.OK).body(new UserResponseDto("로그인 성공",HttpStatus.OK.value()));
    }

}
