package com.sparta.board.controller;


import com.sparta.board.dto.CommentRequestDto;
import com.sparta.board.dto.CommentResponseDto;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "댓글", description = "댓글 API 입니다.")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 추가 메서드", description = "특정 글의 댓글을 달아주는 메서드")
    @PostMapping("/comment/{id}")
    public ResponseEntity<?> addComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto,  @AuthenticationPrincipal UserDetailsImpl userDetails){

        return commentService.addComment(id, commentRequestDto,userDetails.getUser());

    }

    @Operation(summary = "댓글 수정 메서드", description = "댓글 수정 메서드")
    @PutMapping("/comment/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto,  @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateComment(id,commentRequestDto,userDetails.getUser());
    }
    @Operation(summary = "댓글 삭제 메서드", description = "댓글 삭제 메서드")
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id,  @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(id,userDetails.getUser());
    }

}
