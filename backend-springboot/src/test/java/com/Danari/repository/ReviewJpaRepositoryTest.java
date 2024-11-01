package com.Danari.repository;

import com.Danari.domain.Club;
import com.Danari.domain.Member;
import com.Danari.domain.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReviewJpaRepositoryTest {
    @Autowired
    private ReviewJpaRepository reviewJpaRepository;
    @Autowired
    private ClubJpaRepository clubJpaRepository;
    @Autowired
    private MemberJpaRepository memberJpaRepository;

    public Member testMember;
    public Club testClub;
    public Review review1;
    public Review review2;

    @BeforeEach
    void setup(){
        // testMember 생성 및 저장
        testMember = new Member("김승호", 32190789, "username(ID)","password");
        memberJpaRepository.save(testMember);

        // testClub 생성 및 저장
        testClub = new Club("밴드 동아리","101","공연예술분과","밴드 동아리입니다.");
        clubJpaRepository.save(testClub);

        // review1 생성 및 필드 설정
        review1 = Review.builder().reviewContent("1번 리뷰입니다.").build();
        review1.createReview(testMember, testClub);

        // review1 생성 및 필드 설정
        review2 = Review.builder().reviewContent("2번 리뷰입니다.").build();;
        review2.createReview(testMember, testClub);

        reviewJpaRepository.save(review1);
        reviewJpaRepository.save(review2);
    }

    @Test
    void testFindReviewsByClubId(){
        List<Review> foundReviews = reviewJpaRepository.findReviewsByClubId(testClub.getId());
        Assertions.assertThat(foundReviews).hasSize(2);

        Assertions.assertThat(foundReviews.get(0).getClub().getId()).isEqualTo(testClub.getId());
        Assertions.assertThat(foundReviews.get(0).getClub().getClubName()).isEqualTo(testClub.getClubName());
        Assertions.assertThat(foundReviews.get(0).getAuthor().getId()).isEqualTo(testMember.getId());
        Assertions.assertThat(foundReviews.get(0).getAuthor().getName()).isEqualTo(testMember.getName());
        Assertions.assertThat(foundReviews.get(0).getReviewContent()).isEqualTo(review1.getReviewContent());

        Assertions.assertThat(foundReviews.get(1).getClub().getId()).isEqualTo(testClub.getId());
        Assertions.assertThat(foundReviews.get(1).getClub().getClubName()).isEqualTo(testClub.getClubName());
        Assertions.assertThat(foundReviews.get(1).getAuthor().getId()).isEqualTo(testMember.getId());
        Assertions.assertThat(foundReviews.get(1).getAuthor().getName()).isEqualTo(testMember.getName());
        Assertions.assertThat(foundReviews.get(1).getReviewContent()).isEqualTo(review2.getReviewContent());
    }
    @Test
    void testFindReviewsByAuthorId(){
        List<Review> foundReviews = reviewJpaRepository.findReviewsByAuthorId(testMember.getId());

        Assertions.assertThat(foundReviews).hasSize(2);

        Assertions.assertThat(foundReviews.get(0).getClub().getId()).isEqualTo(testClub.getId());
        Assertions.assertThat(foundReviews.get(0).getClub().getClubName()).isEqualTo(testClub.getClubName());
        Assertions.assertThat(foundReviews.get(0).getAuthor().getId()).isEqualTo(testMember.getId());
        Assertions.assertThat(foundReviews.get(0).getAuthor().getName()).isEqualTo(testMember.getName());
        Assertions.assertThat(foundReviews.get(0).getReviewContent()).isEqualTo(review1.getReviewContent());

        Assertions.assertThat(foundReviews.get(1).getClub().getId()).isEqualTo(testClub.getId());
        Assertions.assertThat(foundReviews.get(1).getClub().getClubName()).isEqualTo(testClub.getClubName());
        Assertions.assertThat(foundReviews.get(1).getAuthor().getId()).isEqualTo(testMember.getId());
        Assertions.assertThat(foundReviews.get(1).getAuthor().getName()).isEqualTo(testMember.getName());
        Assertions.assertThat(foundReviews.get(1).getReviewContent()).isEqualTo(review2.getReviewContent());
    }
}