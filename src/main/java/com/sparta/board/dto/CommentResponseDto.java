package com.sparta.board.dto;

import com.sparta.board.entity.Comment;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long id;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime modifyedAt;
    private String username;


    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createAt = comment.getCreatedAt();
        this.modifyedAt = comment.getModifiedAt();
        this.username = comment.getUsers().getUsername();

    }

}
