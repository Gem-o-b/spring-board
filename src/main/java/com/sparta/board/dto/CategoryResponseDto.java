package com.sparta.board.dto;

import com.sparta.board.entity.Category;
import lombok.Builder;
import lombok.Getter;


@Getter
public class CategoryResponseDto {

    private Long id;
    private String title;


    @Builder
    private CategoryResponseDto(Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
    }

    public static CategoryResponseDto from(Category category){
        return CategoryResponseDto.builder()
                .category(category)
                .build();
    }
}
