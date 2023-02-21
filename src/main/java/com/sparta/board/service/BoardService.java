package com.sparta.board.service;

import com.sparta.board.dto.*;
import com.sparta.board.entity.*;
import com.sparta.board.exception.CustomException;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.CommentRepository;
import com.sparta.board.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;



    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoard(Pageable pageable){ // BoardResponseDto형식의 리스트로 리턴
        List<Board> board = boardRepository.findAllByOrderByCreatedAtDesc(pageable); // board리스트형으로 Repo에서 생성일 순으로 리턴

        List<BoardResponseDto> boardResponseDtos = new ArrayList<>(); // Stream으로 안할경우

        for(Board boards : board){
            List<CommentResponseDto> commentList = boards.getCommentList().stream().map(CommentResponseDto::from).sorted(Comparator.comparing(CommentResponseDto::getCreateAt).reversed()).toList();
            boardResponseDtos.add(BoardResponseDto.from(boards,commentList));
        }

      /*  List<BoardResponseDto> boardResponseDtos = board.stream() //Board형의 리스트를 boardResponseDto형으로 변경하기 위해 Stream사용
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());*/


        return boardResponseDtos ;
    }

    public BoardResponseDto getIdBoard(Long id) { // BoardResponseDto형식의 리스트로 리턴
        Board board = boardRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 글이 없습니다")); //findId를 했을때 아이디값이 있으면 board로 리턴 아닐경우 경고 리턴

        List<CommentResponseDto> commentList = board.getCommentList().stream().map(CommentResponseDto::from).sorted(Comparator.comparing(CommentResponseDto::getCreateAt).reversed()).toList();




        return BoardResponseDto.from(board,commentList); // Board형으로 받은 값을 BoardResponseDto형으로 변환
    }


    @Transactional
    public ResponseEntity<Object> addBoard(BoardRequestDto boardRequestDto, Users user) {
            Board board = boardRepository.saveAndFlush(Board.of(boardRequestDto, user));
            return ResponseEntity.status(HttpStatus.OK).body(BoardAddResponseDto.from(board));

    }
    @Transactional
    public ResponseEntity<Object> updateBoard(Long id, BoardRequestDto boardRequestDto, Users users) {
        if (boardRepository.findById(id).isEmpty()) {
            throw new CustomException(ExceptionEnum.NOT_EXIST_BOARD);
        }

        if (users.getUserAuthority() == UserAuthority.ADMIN) {
            Board board = boardRepository.findById(id).get();
            board.update(boardRequestDto);
            boardRepository.flush();
            return ResponseEntity.status(HttpStatus.OK).body(
                    BoardAddResponseDto.from(board)
            );
        }

        Board board = boardRepository.findByIdAndUsersId(id, users.getId());
        if (board == null) {
            throw new CustomException(ExceptionEnum.NOT_MY_CONTENT_DELETE);
        }
        board.update(boardRequestDto);
        boardRepository.flush();
        return ResponseEntity.status(HttpStatus.OK).body(
                BoardAddResponseDto.from(board)
        );
    }


    @Transactional
    public ResponseEntity<?> deleteBoard(Long id, Users users) {
        if (boardRepository.findById(id).isEmpty()) {
            throw new CustomException(ExceptionEnum.NOT_EXIST_BOARD);
        }
        if (users.getUserAuthority() == UserAuthority.ADMIN) {
            boardRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    ResultResponseDto.builder()
                            .msg("게시글 삭제 성공")
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }

        Board board = boardRepository.findByIdAndUsersId(id, users.getId());
        if (board == null) {
            throw new CustomException(ExceptionEnum.NOT_MY_CONTENT_DELETE);
        }
        boardRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResultResponseDto.from("게시글 삭제 성공", HttpStatus.OK.value()));

    }
}
