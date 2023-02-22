package com.sparta.board.entity;

import com.sparta.board.dto.BoardRequestDto;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) // @Entity는 @NoArgs 있어야 JPA에서 DDL 가능 -> 기본생성이 public 이니 protected로 변경
public class Board extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 100) // @Column 은 DDL을 만들려고 String 기준 varchar(255) 생성됨으로 줄여주는게 좋음 (String에만 해당)
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

    @OneToMany(mappedBy = "board", cascade =CascadeType.REMOVE )
    private List<Comment> commentList = new ArrayList<>(); // 배열이 초기화가 안되어있으면 NullPointException 떨어짐

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    List<Likes> likes = new ArrayList<>();

    @ManyToOne
    private Category category;

    @Builder
    private Board(BoardRequestDto boardRequestDto,Users users,Category category) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
        this.users = users;
        this.category = category;
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
    }

    public static Board of(BoardRequestDto boardRequestDto, Users user,Category category){
        return Board.builder()
                .boardRequestDto(boardRequestDto)
                .category(category)
                .users(user)
                .build();

    }
}
