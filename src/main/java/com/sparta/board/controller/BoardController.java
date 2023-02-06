package com.sparta.board.controller;

import com.sparta.board.entity.Board;
import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {


    private final BoardService boardService;

    @PostMapping("/api/add")
    public Board addBoard(@RequestBody BoardRequestDto boardRequestDto){

        return boardService.addBoard(boardRequestDto);

    }

    @GetMapping("api/get")
    public List<Board> getBoard(){
        return boardService.getBoard();
    }

    @PutMapping("api/update/{id}")
    public String updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto){
        return boardService.updateBoard(id,boardRequestDto);
    }

    @PostMapping("api/delete/{id}")
    public String deleteBoard(@PathVariable Long id ,@RequestBody BoardRequestDto boardRequestDto){

        return boardService.deleteBoard(id, boardRequestDto);

    }
    @DeleteMapping("api/delete/{id}")
    public String deleteBoard2(@PathVariable Long id ,@RequestParam("password") String password){

        return boardService.deleteBoard2(id, password);


    }


}
