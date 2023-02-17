package com.sparta.board.service;

import com.sparta.board.dto.CommentRequestDto;
import com.sparta.board.dto.CommentResponseDto;
import com.sparta.board.dto.ResultResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import com.sparta.board.entity.UserAuthority;
import com.sparta.board.entity.Users;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.CommentRepository;
import com.sparta.board.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;


    private ResponseEntity<Object> badRequest(String msg){
        return ResponseEntity.badRequest().body(ResultResponseDto.builder()
                .msg(msg)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build());
    }
    @Transactional
    public ResponseEntity<?> addComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims; // JWT 구성요소 꼭 필요하진 않음 payload안에 담겨있는 데이터
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
//                throw new IllegalArgumentException("Token Error");
                return badRequest("토큰이 유효하지 않습니다");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            if (userRepository.findByUsername(claims.getSubject()).isEmpty()){
                return badRequest("사용자가 없습니다");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            Users users = userRepository.findByUsername(claims.getSubject()).get();

            if (boardRepository.findById(id).isEmpty()){
                return badRequest("해당 글이 없습니다");
            }else {
                Board board = boardRepository.findById(id).get();
                Comment comment = commentRepository.saveAndFlush(new Comment(commentRequestDto,users,board));
                return ResponseEntity.status(HttpStatus.OK).body(new CommentResponseDto(comment));
            }
        }
        return badRequest("토큰이 유효하지 않습니다"); // 마지막 리턴은 성공으로. 위에서부터 큰 케이스로 걸러낼것.
    }
    @Transactional
    public ResponseEntity<?> updateComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
//                throw new IllegalArgumentException("Token Error");
                return badRequest("토큰이 유효하지 않습니다2");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            if (userRepository.findByUsername(claims.getSubject()).isEmpty()){
                return badRequest("사용자가 없습니다");
            }

            Users users = userRepository.findByUsername(claims.getSubject()).get();

            if(users.getUserAuthority()== UserAuthority.ADMIN){
                if (commentRepository.findById(id).isEmpty()){
                    return badRequest("글이 없습니다");
                }else{
                    Comment comment = commentRepository.findById(id).get();
                    comment.update(commentRequestDto);
                    return ResponseEntity.status(HttpStatus.OK).body(new CommentResponseDto(comment));

                }

            }else{
                Comment comment = commentRepository.findByIdAndUsersId(id,users.getId());
                System.out.println(comment);
                if(comment == null){
                    return badRequest("글이 없습니다");
                }else{
                    comment.update(commentRequestDto);
                    return ResponseEntity.status(HttpStatus.OK).body(new CommentResponseDto(comment));
                }
            }
        }

        return badRequest("토큰이 유효하지 않습니다1");

    }

    @Transactional
    public ResponseEntity<?> deleteComment(Long id,HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
//                throw new IllegalArgumentException("Token Error");
                return badRequest("토큰이 유효하지 않습니다");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            if (userRepository.findByUsername(claims.getSubject()).isEmpty()){
                return badRequest("사용자가 없습니다");
            }
            Users users = userRepository.findByUsername(claims.getSubject()).get();

            if(users.getUserAuthority() == UserAuthority.ADMIN){
                if (commentRepository.findById(id).isEmpty()){
                    return badRequest("글이 없습니다");
                }else {
                    commentRepository.deleteById(id);
                    return ResponseEntity.status(HttpStatus.OK).body(
                            ResultResponseDto.builder()
                                    .msg("삭제 완료")
                                    .statusCode(HttpStatus.OK.value())
                                    .build()
                    );
                }

            }else{
                Comment comment = commentRepository.findByIdAndUsersId(id, users.getId());
                if (comment == null){
                    return badRequest("글이 없습니다");
                }else{
                    commentRepository.deleteById(id);
                    return ResponseEntity.status(HttpStatus.OK).body(
                            ResultResponseDto.builder()
                                    .msg("삭제 완료")
                                    .statusCode(HttpStatus.OK.value())
                                    .build()
                    );
                }
            }

        }
        return badRequest("토큰이 유효하지 않습니다");
    }
}
