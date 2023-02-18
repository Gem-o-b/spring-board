package com.sparta.board.service;

import com.sparta.board.dto.ResultResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import com.sparta.board.entity.Likes;
import com.sparta.board.entity.Users;
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

        Optional<Likes> likes = likeRepository.findByBoard_IdAndUsers_Id(id, user.getId());

        if(likes.isPresent()){
            likeRepository.delete(likes.get());
            return ResponseEntity.ok().body(new ResultResponseDto("좋아요 삭제", HttpStatus.OK.value()));
        }

        if (!boardRepository.findById(id).isEmpty()) {
            Board board = boardRepository.findById(id).get();
            likeRepository.saveAndFlush(new Likes(board,user,null));
            return ResponseEntity.ok().body(new ResultResponseDto("좋아요 추가", HttpStatus.OK.value()));
        }

        return ResponseEntity.badRequest().body(new ResultResponseDto("글이 없습니다", HttpStatus.BAD_REQUEST.value()));


//        likeRepository.findByBoard_IdAndUsers_Id(id, user.getId());
    }

    public ResponseEntity<?> likeComment(Long id, Users user) {
        Optional<Likes> likes = likeRepository.findByComment_IdAndUsers_Id(id, user.getId());

        if(likes.isPresent()){
            likeRepository.delete(likes.get());
            return ResponseEntity.ok().body(new ResultResponseDto("좋아요 삭제", HttpStatus.OK.value()));
        }

        if (!boardRepository.findById(id).isEmpty()) {
            Comment comment = commentRepository.findById(id).get();
            likeRepository.saveAndFlush(new Likes(null,user,comment));
            return ResponseEntity.ok().body(new ResultResponseDto("좋아요 추가", HttpStatus.OK.value()));
        }

        return ResponseEntity.badRequest().body(new ResultResponseDto("글이 없습니다", HttpStatus.BAD_REQUEST.value()));

    }
}