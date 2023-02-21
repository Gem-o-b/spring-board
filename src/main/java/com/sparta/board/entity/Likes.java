package com.sparta.board.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    Board board;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    Users users;

    @ManyToOne
    Comment comment;

    @Builder
    private Likes(Board board, Users users, Comment comment) {
        this.board = board;
        this.users = users;
        this.comment = comment;
    }

    public static Likes of(Board board, Users users){
        return Likes.builder()
                .board(board)
                .users(users)
                .build();
    }

    public static Likes of(Comment comment, Users users){
        return Likes.builder()
                .comment(comment)
                .users(users)
                .build();
    }

}
