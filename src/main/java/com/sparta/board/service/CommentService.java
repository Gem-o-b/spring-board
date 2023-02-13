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

    @Transactional
    public CommentResponseDto addComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 최저가 업데이트 가능
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            Users user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            if (boardRepository.findById(id).isEmpty()){
                throw new IllegalArgumentException("해당 글이 없습니다");
            }else {
                Board board = boardRepository.findById(id).get();
                Comment comment = commentRepository.saveAndFlush(new Comment(commentRequestDto,user,board));
                return new CommentResponseDto(comment);
            }
        }

        return null;

    }
    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            Users user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );


            if(user.getUserAuthority()== UserAuthority.ADMIN){
                if (commentRepository.findById(id).isEmpty()){
                    throw new IllegalArgumentException("글이 없습니다");
                }else{
                    Comment comment = commentRepository.findById(id).get();
                    comment.update(commentRequestDto);
                    return new CommentResponseDto(comment);
                }

            }else{
                Comment comment = commentRepository.findByIdAndUsersId(id,user.getId());
                if(comment == null){
                    throw new IllegalArgumentException("글이 없습니다");
                }else{
                    comment.update(commentRequestDto);
                    return new CommentResponseDto(comment);
                }
            }
        }

        return null;

    }

    @Transactional
    public ResponseEntity<Object> deleteComment(Long id,HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            Users user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            if(user.getUserAuthority() == UserAuthority.ADMIN){
                if (commentRepository.findById(id).isEmpty()){
                    return ResponseEntity.badRequest().body(
                            ResultResponseDto.builder()
                                    .msg("글이 없습니다")
                                    .statusCode(HttpStatus.BAD_REQUEST.value())
                                    .build()
                    );
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
                Comment comment = commentRepository.findByIdAndUsersId(id, user.getId());
                if (comment == null){
                    return ResponseEntity.badRequest().body(
                            ResultResponseDto.builder()
                                    .msg("글이 없습니다")
                                    .statusCode(HttpStatus.BAD_REQUEST.value())
                                    .build()
                    );
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
        return null;
    }
}
