package com.sparta.board.controller;

import com.sparta.board.dto.BoardAddResponseDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    @PostMapping("/api/post") // 추가
    public ResponseEntity<Object> addBoard(@RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return boardService.addBoard(boardRequestDto,userDetails.getUser());

    }

    @GetMapping("api/posts") // 전체 조회
    public List<BoardResponseDto> getBoard(){
        return boardService.getBoard();
    }

    @GetMapping("api/post/{id}") // 특정 글 조회
    public BoardResponseDto getIdBoard(@PathVariable Long id){
        return boardService.getIdBoard(id);
    }

    @PutMapping("api/post/{id}") // 수정
    public ResponseEntity<Object> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return boardService.updateBoard(id,boardRequestDto, userDetails.getUser());
    }
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
