package com.Danari.repository;

import com.Danari.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventPostJpaRepository extends JpaRepository<Post, Long> {
    List<Post> findEventPostsByClubId(Long clubId); // 동아리 id로 행사글 조회
}
