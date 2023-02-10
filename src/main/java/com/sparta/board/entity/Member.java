package com.sparta.board.entity;

import com.sparta.board.dto.MemberRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany
    List<Board> boards = new ArrayList<>();


    public Member(MemberRequestDto memberRequestDto) {
        this.username = memberRequestDto.getUsername();
        this.password = memberRequestDto.getPassword();
    }


}
