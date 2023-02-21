package com.sparta.board.repository;

import com.sparta.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
        List<Board> findAllByOrderByModifiedAtDesc();
        List<Board> findAllByOrderByCreatedAtDesc();

//        Optional<Board> findByIdAndUserName(Long id, String username);
//        Optional<Board> findByIdAndUsersId(Long id, Long usersid);
        Board findByIdAndUsersId(Long id, Long usersid);
        List<Board> findByUsersId(Long usersid);

}
