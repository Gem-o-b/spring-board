package com.sparta.board.dto;

import com.sparta.board.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardAddResponseDto {
    private Long id ;
    private String title;
    private String userName;
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public BoardAddResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.userName = board.getUserName();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }
}
