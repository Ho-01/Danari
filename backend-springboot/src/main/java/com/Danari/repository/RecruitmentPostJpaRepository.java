package com.Danari.repository;

import com.Danari.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecruitmentPostJpaRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.club.id = :clubId AND p.postType = 'CLUB_RECRUITMENT'")
    List<Post> findRecruitmentPostsByClubId(@Param("clubId") Long clubId); // 동아리 id로 모집글 조회
}
