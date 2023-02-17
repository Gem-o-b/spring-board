package com.sparta.board.controller;


import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likeService;
    @PostMapping("/like/board/{id}")
    private ResponseEntity<?> likeBoard(@PathVariable Long id , @AuthenticationPrincipal UserDetailsImpl userDetails){
        return likeService.likeBoard(id,userDetails.getUser());
    }

    @PostMapping("/like/comment/{id}")
    private ResponseEntity<?> likeComment(@PathVariable Long id ,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return likeService.likeComment(id,userDetails.getUser());
    }

}
