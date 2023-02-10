package com.sparta.board.service;

import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.dto.BoardRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoard(){ // BoardResponseDto형식의 리스트로 리턴
        List<Board> board = boardRepository.findAllByOrderByCreatedAtDesc(); // board리스트형으로 Repo에서 생성일 순으로 리턴
        List<BoardResponseDto> boardResponseDtos = board.stream() //Board형의 리스트를 boardResponseDto형으로 변경하기 위해 Stream사용
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
        return boardResponseDtos ;
    }

    public BoardResponseDto getIdBoard(Long id) { // BoardResponseDto형식의 리스트로 리턴
        Board board = boardRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 글이 없습니다")); //findId를 했을때 아이디값이 있으면 board로 리턴 아닐경우 경고 리턴

        return new BoardResponseDto(board); // Board형으로 받은 값을 BoardResponseDto형으로 변환
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

    @Transactional
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
 /* @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("자료가 없습니다"));
        if (!board.getPassword().equals(boardRequestDto.getPassword())) {
        }
        board.update(boardRequestDto);
        return new BoardResponseDto(board);
    }*/


//    @Transactional
//    public String deleteBoard2(Long id, String password) {
//        Board board = boardRepository.findById(id).orElseThrow(
//                ()-> new IllegalArgumentException("자료가 없습니다")
//        );
//        if(!board.getPassword().equals(password)){
//            return "잘못된 패스워드 입니다";
//        }
//        boardRepository.delete(board);
//        return "삭제 완료";
//    }
//

}
