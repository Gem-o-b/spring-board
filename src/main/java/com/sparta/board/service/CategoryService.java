package com.sparta.board.service;


import com.sparta.board.dto.CategoryRequestDto;
import com.sparta.board.dto.CategoryResponseDto;
import com.sparta.board.dto.ResultResponseDto;
import com.sparta.board.entity.Category;
import com.sparta.board.entity.ExceptionEnum;
import com.sparta.board.exception.CustomException;
import com.sparta.board.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    @Transactional
    public ResponseEntity<?> categoryAdd(CategoryRequestDto categoryRequestDto) {

        Category category = Category.builder()
                .title(categoryRequestDto.getTitle())
                .build();
        categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.OK).body(ResultResponseDto.builder()
                .msg("카테고리 추가 완료").statusCode(HttpStatus.OK.value())
                .build());
    }

    @Transactional
    public CategoryResponseDto categoryUpdate(Long id , CategoryRequestDto categoryRequestDto) {

        if (categoryRepository.findById(id).isEmpty()){
            throw new CustomException(ExceptionEnum.NOT_EXIST_CATEGORY);
        }
        Category category = categoryRepository.findById(id).get();
        category.update(categoryRequestDto);
        return CategoryResponseDto.from(category);
    }

    public ResponseEntity<ResultResponseDto> categoryDelete(Long id) {
        if (categoryRepository.findById(id).isEmpty()){
            throw new CustomException(ExceptionEnum.NOT_EXIST_CATEGORY);
        }
        Category category = categoryRepository.findById(id).get();
        categoryRepository.delete(category);
        return ResponseEntity.status(HttpStatus.OK).body(ResultResponseDto.builder().msg("카테고리 삭제 완료").statusCode(HttpStatus.OK.value()).build());
    }

    public List<CategoryResponseDto> categoryGet() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponseDto> categoryResponseDtoList = new ArrayList<>();
        for (Category category:categoryList){
            categoryResponseDtoList.add(CategoryResponseDto.from(category));
        }
        return categoryResponseDtoList;
    }
}
