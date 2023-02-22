package com.sparta.board.entity;

import com.sparta.board.dto.CategoryRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;


    public void update(CategoryRequestDto categoryRequestDto){
        this.title = categoryRequestDto.getTitle();
    }

    @Builder
    private Category(String title) {
        this.title = title;
    }

    public static Category of(String title){
        return Category.builder()
                .title(title)
                .build();

    }

}
