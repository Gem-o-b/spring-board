package com.sparta.board.controller;

import com.sparta.board.dto.BoardAddResponseDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Tag(name = "게시판", description = "게시판 API 입니다.")
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "게시글 추가 메서드", description = "게시글 추가 메서드 입니다.")

    @PostMapping("/api/post") // 추가
    public ResponseEntity<Object> addBoard(@RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return boardService.addBoard(boardRequestDto,userDetails.getUser());

    }
    @Operation(summary = "전체조회 메서드", description = "전체 조회 메서드 입니다.")
    @GetMapping("api/posts") // 전체 조회
    public List<BoardResponseDto> getBoard(Pageable pageable){
        return boardService.getBoard(pageable);
    }

    @Operation(summary = "특정 글 조회 메서드", description = "특정 글 조회 메서드 입니다.")
    @Parameter(name = "id", description = "글의 ID값")
    @GetMapping("api/post/{id}") // 특정 글 조회
    public BoardResponseDto getIdBoard(@PathVariable Long id){
        return boardService.getIdBoard(id);
    }

    @Operation(summary = "특정 글 업데이트 메서드", description = "특정 글 업데이트 메서드 입니다.")
    @PutMapping("api/post/{id}") // 수정
    public ResponseEntity<Object> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return boardService.updateBoard(id,boardRequestDto, userDetails.getUser());
    }
    @Operation(summary = "특정 글 삭제 메서드", description = "특정 글 삭제 메서드 입니다.")
    @DeleteMapping("api/post/{id}") // 삭제
    public ResponseEntity<?> deleteBoard(@PathVariable Long id , @AuthenticationPrincipal UserDetailsImpl userDetails){

        return boardService.deleteBoard(id,userDetails.getUser());


    }


   /* @PutMapping("api/update/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto){
        return boardService.updateBoard(id,boardRequestDto);
    }*/

//    @PostMapping("api/delete/{id}")
//    public String deleteBoard(@PathVariable Long id ,@RequestBody BoardRequestDto boardRequestDto){
//
//        return boardService.deleteBoard(id, boardRequestDto);
//
//    }
//    @DeleteMapping("api/delete/{id}")
//    public String deleteBoard2(@PathVariable Long id ,@RequestParam("password") String password){
//
//        return boardService.deleteBoard2(id, password);
//
//
//    }


}
