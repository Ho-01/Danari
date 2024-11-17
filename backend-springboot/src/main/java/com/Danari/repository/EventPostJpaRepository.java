package com.Danari.repository;

import com.Danari.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventPostJpaRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.club.Id = :clubId AND p.postType = 'CLUB_EVENT'")
    List<Post> findEventPostsByClubId(@Param("clubId") Long clubId); // 동아리 id로 행사글 조회
}
