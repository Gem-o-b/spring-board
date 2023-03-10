package com.sparta.board.dto;

import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Getter // ResponseDto에서 @Getter사용 이유 -> JPA 맵핑 못함 찾아보셈
@Setter
public class BoardResponseDto {
    private Long id ;
    private String title;
    private String userName;
    private String content;

    private List<CommentResponseDto> commentList;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.userName = board.getUsers().getUsername();
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.commentList = board.getCommentList().stream().map(i-> new CommentResponseDto(i)).sorted(Comparator.comparing(CommentResponseDto::getCreateAt).reversed()).toList(); //수정해보기
    }
}
