package com.sparta.board.dto;

import com.sparta.board.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {
    private Long id ;
    private String title;
    private String userName;
    private String content;

    private LocalDateTime createdAt;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.userName = board.getUserName();
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();
    }
}
