package com.sparta.board.controller;


import com.sparta.board.dto.CommentRequestDto;
import com.sparta.board.dto.CommentResponseDto;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("api/")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/{id}")
    public ResponseEntity<?> addComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto,  @AuthenticationPrincipal UserDetailsImpl userDetails){

        return commentService.addComment(id, commentRequestDto,userDetails.getUser());

    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto,  @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateComment(id,commentRequestDto,userDetails.getUser());
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id,  @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(id,userDetails.getUser());
    }

}
