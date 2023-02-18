package com.sparta.board.service;


import com.sparta.board.dto.UserRequestDto;
import com.sparta.board.dto.ResultResponseDto;
import com.sparta.board.entity.ExceptionEnum;
import com.sparta.board.entity.Users;
import com.sparta.board.exception.CustomException;
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



    @Transactional
    public ResponseEntity<Object> addMember(UserRequestDto userRequestDto) {
        String userID = userRequestDto.getUsername();
        String userPW = passwordEncoder.encode(userRequestDto.getPassword());
        boolean userAuthority = userRequestDto.isIsadmin();

        Users users = new Users(userID,userPW,userAuthority);
        Optional<Users> rst = userRepository.findByUsername(userRequestDto.getUsername());

        if (rst.isPresent()){
            throw new CustomException(ExceptionEnum.DUPLICATE_USER);
        }
        userRepository.save(users);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponseDto("회원가입 성공",HttpStatus.OK.value()));

    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> login(UserRequestDto userRequestDto, HttpServletResponse response) {

        String username = userRequestDto.getUsername();
        String password = userRequestDto.getPassword();

        if (userRepository.findByUsername(username).isEmpty()){
//            return badRequest("등록된 사용자가 없습니다");
            throw new CustomException(ExceptionEnum.NOT_EXIST_USER);
        }
        Users users = userRepository.findByUsername(username).get();
//        if(!users.getPassword().equals(password)){
        if(!passwordEncoder.matches(password, users.getPassword())){
            throw new CustomException(ExceptionEnum.PASSWORD_WRONG);
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(users.getUsername()));
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponseDto("로그인 성공",HttpStatus.OK.value()));
    }

}
