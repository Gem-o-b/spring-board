package com.sparta.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

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
    Users users;

    @ManyToOne
    Comment comment;

    public Likes(Board board, Users users, Comment comment) {
        this.board = board;
        this.users = users;
        this.comment = comment;
    }
}
