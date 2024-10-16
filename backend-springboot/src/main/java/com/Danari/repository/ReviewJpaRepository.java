package com.Danari.repository;

import com.Danari.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {
    List<Review> findReviewsByClubId(Long id); // 동아리 id로 리뷰 조회
    List<Review> findReviewsByAuthorId(Long id); // 작성자 id로 리뷰 조회
}
