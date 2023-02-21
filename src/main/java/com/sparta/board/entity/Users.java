package com.sparta.board.entity;

import com.sparta.board.dto.UserRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private UserAuthority userAuthority;

   /* @OneToMany(mappedBy = "board" ,fetch = FetchType.EAGER)
    List<Board> boards = new ArrayList<>();*/


    @Builder
    private Users(String userName,String userPassword, UserAuthority userAuthority) {
        this.username = userName;
        this.password = userPassword;
        this.userAuthority = userAuthority;
    }

    public static Users of(String userName,String userPassword, UserAuthority userAuthority){
        return Users.builder()
                .userName(userName)
                .userPassword(userPassword)
                .userAuthority(userAuthority)
                .build();
    }


}
