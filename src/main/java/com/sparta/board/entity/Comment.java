package com.sparta.board.entity;

import com.sparta.board.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "comment",cascade = CascadeType.REMOVE)
    @Column
    private List<Likes> likesList = new ArrayList<>();


    public Comment(CommentRequestDto commentRequestDto, Users users, Board board){
        this.content = commentRequestDto.getContent();
        this.users = users;
        this.board = board;

    }

    public void update(CommentRequestDto commentRequestDto){
        this.content = commentRequestDto.getContent();
    }

}
