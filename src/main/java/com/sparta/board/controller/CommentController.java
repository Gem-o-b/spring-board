package com.sparta.board.controller;


import com.sparta.board.dto.CommentRequestDto;
import com.sparta.board.dto.CommentResponseDto;
import com.sparta.board.service.CommentService;
import lombok.RequiredArgsConstructor;
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
    public CommentResponseDto addComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request){

        return commentService.addComment(id, commentRequestDto,request);

    }

    @PutMapping("/comment/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request ){
        return commentService.updateComment(id,commentRequestDto,request);
    }

    @DeleteMapping("/comment/{id}")
    public void deleteComment(@PathVariable Long id){

    }

}
