package com.Danari.repository;

import com.Danari.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitmentPostJpaRepository extends JpaRepository<Post, Long> {
    List<Post> findRecruitmentPostsByClubId(Long id); // 동아리 id로 모집글 조회
}
