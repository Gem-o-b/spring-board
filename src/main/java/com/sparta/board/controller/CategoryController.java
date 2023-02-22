package com.sparta.board.controller;


import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.dto.CategoryRequestDto;
import com.sparta.board.dto.CategoryResponseDto;
import com.sparta.board.dto.ResultResponseDto;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "카테고리", description = "카테고리 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 추가 메서드", description = "카테고리 추가 메서드 입니다.")
    @PostMapping("/post")
    public ResponseEntity<?> categoryAdd(@RequestBody CategoryRequestDto categoryRequestDto){

        return categoryService.categoryAdd(categoryRequestDto);

    }

    @Operation(summary = "카테고리 조회 메서드", description = "카테고리 조회 메서드 입니다.")
    @GetMapping("/post")
    public List<CategoryResponseDto> categoryGet(){
        return categoryService.categoryGet();
    }



    @Operation(summary = "카테고리 수정 메서드", description = "카테고리 수정 메서드 입니다.")
    @PutMapping("/post/{id}")
    public CategoryResponseDto categoryUpdate(@PathVariable Long id, @RequestBody CategoryRequestDto categoryRequestDto){

        return categoryService.categoryUpdate(id,categoryRequestDto);

    }

    @Operation(summary = "카테고리 삭제 메서드", description = "카테고리 삭제 메서드 입니다.")
    @DeleteMapping("/post/{id}")
    public ResponseEntity<ResultResponseDto> categoryUpdate(@PathVariable Long id){

        return categoryService.categoryDelete(id);

    }

    @Operation(summary = "카테고리별 게시글 메서드", description = "카테고리별 게시글 조회 메서드 입니다.")
    @GetMapping("/post/{id}")
    public List<BoardResponseDto> categoryGetId(@PathVariable Long id){

        return categoryService.categoryGetId(id);

    }


}
