package com.sparta.board.repository;

import com.sparta.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    Comment findByIdAndUsersId(Long id, Long userid);

    List<Comment> findByBoard_Id(Long boardid);
    List<Comment> findByUsersId(Long userid);

//    List<Comment> findByBoard_IdOOrderByIdAsc(Long id);
}
