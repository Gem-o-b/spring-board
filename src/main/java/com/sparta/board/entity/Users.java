package com.sparta.board.entity;

import com.sparta.board.dto.UserRequestDto;
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



    public Users(UserRequestDto userRequestDto) {
        this.username = userRequestDto.getUsername();
        this.password = userRequestDto.getPassword();
        System.out.println(userRequestDto.isIsadmin());
        if(userRequestDto.isIsadmin()==true){
            this.userAuthority = UserAuthority.ADMIN;
        }else{
            this.userAuthority = UserAuthority.USER;
        }
    }


}
