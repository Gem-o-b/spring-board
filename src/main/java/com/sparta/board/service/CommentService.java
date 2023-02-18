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
    public ResponseEntity<?> addComment(Long id, CommentRequestDto commentRequestDto,Users users) {


            if (boardRepository.findById(id).isEmpty()){
                return badRequest("해당 글이 없습니다");
            }
                Board board = boardRepository.findById(id).get();
                Comment comment = commentRepository.saveAndFlush(new Comment(commentRequestDto,users,board));
                return ResponseEntity.status(HttpStatus.OK).body(new CommentResponseDto(comment));

    }
    @Transactional
    public ResponseEntity<?> updateComment(Long id, CommentRequestDto commentRequestDto, Users users) {

        if (commentRepository.findById(id).isEmpty()) {
            return badRequest("글이 없습니다");
        }
        if (users.getUserAuthority() == UserAuthority.ADMIN) {

            Comment comment = commentRepository.findById(id).get();
            comment.update(commentRequestDto);
            return ResponseEntity.status(HttpStatus.OK).body(new CommentResponseDto(comment));

        }
        Comment comment = commentRepository.findByIdAndUsersId(id, users.getId());
        System.out.println(comment);
        if (comment == null) {
            return badRequest("글이 없습니다");
        }
        comment.update(commentRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(new CommentResponseDto(comment));
    }

    @Transactional
    public ResponseEntity<?> deleteComment(Long id, Users users) {

        if (commentRepository.findById(id).isEmpty()) {
            return badRequest("글이 없습니다");
        }
        if (users.getUserAuthority() == UserAuthority.ADMIN) {
            commentRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    ResultResponseDto.builder()
                            .msg("삭제 완료")
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }
        Comment comment = commentRepository.findByIdAndUsersId(id, users.getId());
        if (comment == null) {
            return badRequest("글이 없습니다");
        }
        commentRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResultResponseDto.builder()
                        .msg("삭제 완료")
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }
}
