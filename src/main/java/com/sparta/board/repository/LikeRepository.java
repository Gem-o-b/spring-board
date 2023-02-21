package com.sparta.board.repository;

import com.sparta.board.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes,Long> {
    Optional<Likes> findByBoard_IdAndUsers_Id(Long boardId, Long usersId);
    Optional<Likes> findByComment_IdAndUsers_Id(Long commentId, Long usersId);

    void deleteByComment_Id(Long commentId);
}
