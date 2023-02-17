package com.sparta.board.service;

import com.sparta.board.dto.*;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import com.sparta.board.entity.UserAuthority;
import com.sparta.board.entity.Users;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.CommentRepository;
import com.sparta.board.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    private ResponseEntity<Object> badRequest(String msg){
        return ResponseEntity.badRequest().body(ResultResponseDto.builder()
                .msg(msg)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build());
    }
    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoard(){ // BoardResponseDto형식의 리스트로 리턴
        List<Board> board = boardRepository.findAllByOrderByCreatedAtDesc(); // board리스트형으로 Repo에서 생성일 순으로 리턴

     /*   List<BoardResponseDto> boardResponseDtos = new ArrayList<>(); // Stream으로 안할경우

        for(Board boards : board){
            boardResponseDtos.add(new BoardResponseDto(boards));
        }*/

        List<BoardResponseDto> boardResponseDtos = board.stream() //Board형의 리스트를 boardResponseDto형으로 변경하기 위해 Stream사용
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());


        return boardResponseDtos ;
    }

    public BoardResponseDto getIdBoard(Long id) { // BoardResponseDto형식의 리스트로 리턴
        Board board = boardRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 글이 없습니다")); //findId를 했을때 아이디값이 있으면 board로 리턴 아닐경우 경고 리턴

//        List<Comment> commentList = commentRepository.findByBoard_IdOOrderByIdAsc(board.getId());


        return new BoardResponseDto(board); // Board형으로 받은 값을 BoardResponseDto형으로 변환
    }


    @Transactional
    public ResponseEntity<Object> addBoard(BoardRequestDto boardRequestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
//                throw new IllegalArgumentException("Token Error");
                return badRequest("토큰이 유효하지 않습니다");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            if (userRepository.findByUsername(claims.getSubject()).isEmpty()){
                return badRequest("사용자가 없습니다");
            }

//            Users users = userRepository.findByUsername(claims.getSubject()).orElseThrow(
//                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
//            );

            Users users = userRepository.findByUsername(claims.getSubject()).get();
            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            Board board = boardRepository.saveAndFlush(new Board(boardRequestDto, users));

//            return new ProductResponseDto(product);
//            return new BoardAddResponseDto(board);
            return ResponseEntity.status(HttpStatus.OK).body(new BoardAddResponseDto(board));

        } else {
            return badRequest("토큰이 유효하지 않습니다");
        }

//        Board board = new Board(boardRequestDto);
//
//        boardRepository.save(board);
//
//        return board;

    }
    @Transactional
    public ResponseEntity<Object> updateBoard(Long id, BoardRequestDto boardRequestDto , HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 최저가 업데이트 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
//                throw new IllegalArgumentException("Token Error");
                return badRequest("토큰이 유효하지 않습니다");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            if (userRepository.findByUsername(claims.getSubject()).isEmpty()){
                return badRequest("사용자가 없습니다");
            }

//            Board board = boardRepository.findByIdAndUserName(id, user.getUsername()).orElseThrow(
//            Board board = boardRepository.findByIdAndUsersId(id, user.getId()).orElseThrow(
//                    () -> new NullPointerException("해당 글은 존재하지 않습니다.")
//            );
            Users users = userRepository.findByUsername(claims.getSubject()).get();

            if(users.getUserAuthority() == UserAuthority.ADMIN){
                if (boardRepository.findById(id).isEmpty()){
                    return badRequest("글이 존재하지 않습니다");
                }else{
                    Board board = boardRepository.findById(id).get();
                    board.update(boardRequestDto);
                    return ResponseEntity.status(HttpStatus.OK).body(
                            new BoardAddResponseDto(board)
                    );
                }
            }else{
                Board board = boardRepository.findByIdAndUsersId(id, users.getId());
                if (board == null){
                    return badRequest("글이 존재하지 않습니다");
                }
                board.update(boardRequestDto);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new BoardAddResponseDto(board)
                );
            }

        } else {
            return badRequest("토큰이 유효하지 않습니다");
        }

//
//        Board board = boardRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("자료가 없습니다"));
//        if(!board.getPassword().equals(boardRequestDto.getPassword()) ){
//            return "잘못된 패스워드 입니다";
//        }
//        board.update(boardRequestDto);
//        return "저장 완료";

    }

    @Transactional
    public ResponseEntity<?> deleteBoard(Long id, BoardRequestDto boardRequestDto , HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
//                throw new IllegalArgumentException("Token Error");
                return badRequest("토큰이 유효하지 않습니다");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            if (userRepository.findByUsername(claims.getSubject()).isEmpty()){
                return badRequest("사용자가 없습니다");
            }


//            Board board = boardRepository.findByIdAndUserName(id, user.getUsername()).orElseThrow(
//                    () -> new NullPointerException("해당 글은 존재하지 않습니다.")
//            );
//            Optional<Board> board = boardRepository.findByIdAndUsersId(id, user.getId());
            Users users = userRepository.findByUsername(claims.getSubject()).get();
            if(users.getUserAuthority() == UserAuthority.ADMIN){
                if (boardRepository.findById(id).isEmpty()){
                    return badRequest("글이 존재하지 않습니다");
                }else{
                    boardRepository.deleteById(id);
                    return ResponseEntity.status(HttpStatus.OK).body(
                            ResultResponseDto.builder()
                                    .msg("게시글 삭제 성공")
                                    .statusCode(HttpStatus.OK.value())
                                    .build()
                    );
                }
            }

            Board board = boardRepository.findByIdAndUsersId(id, users.getId());
            if(board == null){
//                return ResponseEntity.badRequest().body(new UserResponseDto("본인의 글만 삭제 가능합니다",HttpStatus.BAD_REQUEST.value()));
                //빌더패턴 적용
                return badRequest("본인의 글만 삭제 가능합니다");

            }

//            boardRepository.delete(board);
            boardRepository.deleteById(id);

            return ResponseEntity.status(HttpStatus.OK).body(new ResultResponseDto("게시글 삭제 성공",HttpStatus.OK.value()));

        } else {
            return badRequest("토큰이 유효하지 않습니다");
        }

       /* Board board = boardRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("자료가 없습니다")
        );
        if(!board.getPassword().equals(boardRequestDto.getPassword()) ){
            return "잘못된 패스워드 입니다";
        }

        boardRepository.delete(board);
        return "삭제 완료";*/

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
