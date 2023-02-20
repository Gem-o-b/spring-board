package com.sparta.board.controller;


import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "좋아요", description = "좋아요 API 입니다.")
public class LikeController {

    private final LikeService likeService;
    @Operation(summary = "글 좋아요 메서드", description = "글에 좋아요를 추가하거나 빼는 메서드")
    @PostMapping("/like/board/{id}")
    private ResponseEntity<?> likeBoard(@PathVariable Long id , @AuthenticationPrincipal UserDetailsImpl userDetails){
        return likeService.likeBoard(id,userDetails.getUser());
    }

    @Operation(summary = "댓글 좋아요 메서드", description = "댓글에 좋아요를 추가하거나 빼는 메서드")
    @PostMapping("/like/comment/{id}")
    private ResponseEntity<?> likeComment(@PathVariable Long id ,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return likeService.likeComment(id,userDetails.getUser());
    }

}
