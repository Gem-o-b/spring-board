package com.sparta.board.service;

import com.sparta.board.dto.ResultResponseDto;
import com.sparta.board.entity.*;
import com.sparta.board.exception.CustomException;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.CommentRepository;
import com.sparta.board.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ResponseEntity<?> likeBoard(Long id, Users user) {
        if (boardRepository.findById(id).isEmpty()) {
            throw new CustomException(ExceptionEnum.NOT_EXIST_BOARD);

        }
        Optional<Likes> likes = likeRepository.findByBoard_IdAndUsers_Id(id, user.getId());

        if(likes.isPresent()){
            likeRepository.delete(likes.get());
            return ResponseEntity.ok().body(new ResultResponseDto("좋아요 삭제", HttpStatus.OK.value()));
        }


        Board board = boardRepository.findById(id).get();
        likeRepository.saveAndFlush(Likes.of(board,user));
        return ResponseEntity.ok().body(new ResultResponseDto("좋아요 추가", HttpStatus.OK.value()));


//        likeRepository.findByBoard_IdAndUsers_Id(id, user.getId());
    }

    public ResponseEntity<?> likeComment(Long id, Users user) {
        if (commentRepository.findById(id).isEmpty()) {
            throw new CustomException(ExceptionEnum.NOT_EXIST_BOARD);
        }
        Optional<Likes> likes = likeRepository.findByComment_IdAndUsers_Id(id, user.getId());

        if(likes.isPresent()){
            likeRepository.delete(likes.get());
            return ResponseEntity.ok().body(new ResultResponseDto("좋아요 삭제", HttpStatus.OK.value()));
        }


        Comment comment = commentRepository.findById(id).get();
        likeRepository.saveAndFlush(Likes.of(comment,user));
        return ResponseEntity.ok().body(new ResultResponseDto("좋아요 추가", HttpStatus.OK.value()));

    }
}
