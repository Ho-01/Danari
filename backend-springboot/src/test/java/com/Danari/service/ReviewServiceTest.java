package com.Danari.service;

import com.Danari.domain.Club;
import com.Danari.domain.Member;
import com.Danari.domain.MemberGrade;
import com.Danari.domain.Membership;
import com.Danari.dto.ReviewCreateDTO;
import com.Danari.dto.ReviewResponseDTO;
import com.Danari.dto.ReviewUpdateDTO;
import com.Danari.repository.ClubJpaRepository;
import com.Danari.repository.MemberJpaRepository;
import com.Danari.repository.MembershipJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReviewServiceTest {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ClubJpaRepository clubJpaRepository;
    @Autowired
    private MemberJpaRepository memberJpaRepository;
    @Autowired
    private MembershipJpaRepository membershipJpaRepository;
    private Club testClub;
    private Member testMember;
    private Membership testMembership;
    private ReviewCreateDTO reviewCreateDTO;

    @BeforeEach
    void setUp() {
        testClub = Club.builder().clubName("testClub1").department("공연예술분과").roomNumber("101").description("testClub1 동아리입니다.").build();
        clubJpaRepository.save(testClub);

        testMember = Member.builder().name("김승호").studentId(32190789).username("username").password("password").build();
        memberJpaRepository.save(testMember);

        testMembership = Membership.builder().memberGrade(MemberGrade.PRESIDENT).build();
        testMembership.createMembership(testMember, testClub);
        membershipJpaRepository.save(testMembership);

        reviewCreateDTO = new ReviewCreateDTO();
        reviewCreateDTO.setUsername(testMember.getUsername());
        reviewCreateDTO.setClubName(testClub.getClubName());
        reviewCreateDTO.setReviewContent("리뷰 내용입니다.");
    }

    @Test
    void newReviewTest() {
        Assertions.assertThatThrownBy(() ->{
            reviewService.reviewListByClubName(testClub.getClubName());
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 동아리에 리뷰가 존재하지 않습니다.");

        reviewService.newReview(reviewCreateDTO);
        List<ReviewResponseDTO> foundReview = reviewService.reviewListByClubName(testClub.getClubName());
        Assertions.assertThat(foundReview.size()).isEqualTo(1);
    }

    @Test
    void reviewListByClubNameTest() {
        reviewService.newReview(reviewCreateDTO);

        List<ReviewResponseDTO> foundReview = reviewService.reviewListByClubName(testClub.getClubName());
        Assertions.assertThat(foundReview.size()).isEqualTo(1);
        Assertions.assertThat(foundReview.get(0).getUsername()).isEqualTo(testMember.getUsername());
        Assertions.assertThat(foundReview.get(0).getClubName()).isEqualTo(testClub.getClubName());
        Assertions.assertThat(foundReview.get(0).getReviewContent()).isEqualTo(reviewCreateDTO.getReviewContent());
    }

    @Test
    void reviewByIdTest() {
        reviewService.newReview(reviewCreateDTO);
        List<ReviewResponseDTO> foundReview = reviewService.reviewListByClubName(testClub.getClubName());

        Long reviewId = foundReview.get(0).getId();
        ReviewResponseDTO reviewResponseDTO = reviewService.reviewById(reviewId);
        Assertions.assertThat(reviewResponseDTO.getId()).isEqualTo(reviewId);
        Assertions.assertThat(reviewResponseDTO.getUsername()).isEqualTo(reviewCreateDTO.getUsername());
        Assertions.assertThat(reviewResponseDTO.getClubName()).isEqualTo(reviewCreateDTO.getClubName());
        Assertions.assertThat(reviewResponseDTO.getReviewContent()).isEqualTo(reviewCreateDTO.getReviewContent());
    }

    @Test
    void updateReviewTest() {
        reviewService.newReview(reviewCreateDTO);

        List<ReviewResponseDTO> foundReview = reviewService.reviewListByClubName(testClub.getClubName());
        Long reviewId = foundReview.get(0).getId();

        ReviewUpdateDTO reviewUpdateDTO = new ReviewUpdateDTO();
        reviewUpdateDTO.setId(reviewId);
        reviewUpdateDTO.setReviewContent("Updated Review");

        reviewService.updateReview(reviewUpdateDTO);
        ReviewResponseDTO reviewResponseDTO = reviewService.reviewById(reviewId);
        Assertions.assertThat(reviewResponseDTO.getReviewContent()).isEqualTo(reviewUpdateDTO.getReviewContent());
    }

    @Test
    void deleteReviewTest() {
        reviewService.newReview(reviewCreateDTO);
        List<ReviewResponseDTO> foundReviewBefore = reviewService.reviewListByClubName(testClub.getClubName());
        Assertions.assertThat(foundReviewBefore.size()).isEqualTo(1);

        Long reviewId = foundReviewBefore.get(0).getId();
        reviewService.deleteReview(reviewId);

        Assertions.assertThatThrownBy(() ->{
            reviewService.reviewById(reviewId);
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ReviewId에 해당하는 Review를 찾을 수 없음.");

        Assertions.assertThatThrownBy(() ->{
            reviewService.reviewListByClubName(reviewCreateDTO.getClubName());
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 동아리에 리뷰가 존재하지 않습니다.");
    }
}