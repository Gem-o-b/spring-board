package com.sparta.board.service;


import com.sparta.board.dto.UserRequestDto;
import com.sparta.board.dto.ResultResponseDto;
import com.sparta.board.entity.Users;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private ResponseEntity<Object> badRequest(String msg){
        return ResponseEntity.badRequest().body(ResultResponseDto.builder()
                .msg(msg)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build());
    }


    @Transactional
    public ResponseEntity<Object> addMember(UserRequestDto userRequestDto) {
        String userID = userRequestDto.getUsername();
        String userPW = passwordEncoder.encode(userRequestDto.getPassword());
        boolean userAuthority = userRequestDto.isIsadmin();

        Users users = new Users(userID,userPW,userAuthority);
        Optional<Users> rst = userRepository.findByUsername(userRequestDto.getUsername());

        if (rst.isPresent()){
            return badRequest("중복된 사용자가 존재합니다.");
        }
        userRepository.save(users);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponseDto("회원가입 성공",HttpStatus.OK.value()));

    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> login(UserRequestDto userRequestDto, HttpServletResponse response) {

        String username = userRequestDto.getUsername();
        String password = userRequestDto.getPassword();

        if (userRepository.findByUsername(username).isEmpty()){
            return badRequest("등록된 사용자가 없습니다");
        }
        Users users = userRepository.findByUsername(username).get();
//        if(!users.getPassword().equals(password)){
        if(!passwordEncoder.matches(password, users.getPassword())){
            return badRequest("비밀번호가 일치하지 않습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(users.getUsername()));
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponseDto("로그인 성공",HttpStatus.OK.value()));
    }

}
