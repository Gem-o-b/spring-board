package com.sparta.board.entity;

import com.sparta.board.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
//    @Column(nullable = false)
//    private String userName;
    @Column(nullable = false)
    private String content;

/*    @Column(nullable = false)
    private String password;*/

    @ManyToOne
    @JoinColumn(name ="user_id")
    private Users users;

    @OneToMany(mappedBy = "board")
    private List<Comment> commentList = new ArrayList<>();


    public Board(BoardRequestDto boardRequestDto,Users users) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
//        this.password = boardRequestDto.getPassword();
        this.users = users;
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
    }
}
