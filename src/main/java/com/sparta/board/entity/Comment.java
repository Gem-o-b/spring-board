package com.sparta.board.entity;

import com.sparta.board.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name ="user_id")
    private Users users;

    @ManyToOne
    @JoinColumn(name ="board_id")
    private Board board;


    public Comment(CommentRequestDto commentRequestDto, Users users, Board board){
        this.content = commentRequestDto.getComment();
        this.users = users;
        this.board = board;

    }

}
