package com.sparta.board.service;

import com.sparta.board.entity.Board;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.dto.BoardRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public List<Board> getBoard(){
        return boardRepository.findAllByOrderByCreatedAtDesc();
    }

    public Board getIdBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 글이 없습니다"));
        return board;
    }
    @Transactional
    public Board addBoard(BoardRequestDto boardRequestDto) {
        Board board = new Board(boardRequestDto);

        boardRepository.save(board);

        return board;

    }
    @Transactional
    public String updateBoard(Long id, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("자료가 없습니다"));
        if(!board.getPassword().equals(boardRequestDto.getPassword()) ){
            return "잘못된 패스워드 입니다";
        }
        board.update(boardRequestDto);
        return "저장 완료";

    }

    public String deleteBoard(Long id, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("자료가 없습니다")
        );
        if(!board.getPassword().equals(boardRequestDto.getPassword()) ){
            return "잘못된 패스워드 입니다";
        }

        boardRepository.delete(board);
        return "삭제 완료";

    }


    public String deleteBoard2(Long id, String password) {
        Board board = boardRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("자료가 없습니다")
        );
        if(!board.getPassword().equals(password)){
            return "잘못된 패스워드 입니다";
        }
        boardRepository.delete(board);
        return "삭제 완료";
    }


}
