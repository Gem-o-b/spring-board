package com.sparta.board.entity;

import com.sparta.board.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String password;



    public Board(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.userName = boardRequestDto.getUserName();
        this.content = boardRequestDto.getContent();
        this.password = boardRequestDto.getPassword();
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.userName = boardRequestDto.getUserName();
        this.content = boardRequestDto.getContent();
    }
}
