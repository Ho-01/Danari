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
import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Assertions.assertThat(reviewService.reviewListByClubName(testClub.getClubName()).isEmpty()).isEqualTo(true);
        reviewService.newReview(reviewCreateDTO);
        List<ReviewResponseDTO> foundReview = reviewService.reviewListByClubName(testClub.getClubName());
        Assertions.assertThat(foundReview.size()).isEqualTo(1);

        reviewCreateDTO.setClubName("X");
        Assertions.assertThatThrownBy(() -> {
            reviewService.newReview(reviewCreateDTO);
        })
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("동아리를 찾을 수 없습니다. ClubName: "+reviewCreateDTO.getClubName()+" 에 해당하는 동아리 없음");
        reviewCreateDTO.setClubName(testClub.getClubName());

        reviewCreateDTO.setUsername("X");
        Assertions.assertThatThrownBy(() -> {
                    reviewService.newReview(reviewCreateDTO);
                })
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("사용자를 찾을 수 없습니다. Username: "+reviewCreateDTO.getUsername()+" 에 해당하는 사용자 없음");
        reviewCreateDTO.setUsername(testMember.getUsername());
    }

    @Test
    void reviewListByClubNameTest() {
        reviewService.newReview(reviewCreateDTO);

        List<ReviewResponseDTO> foundReview = reviewService.reviewListByClubName(testClub.getClubName());
        Assertions.assertThat(foundReview.size()).isEqualTo(1);
        Assertions.assertThat(foundReview.get(0).getUsername()).isEqualTo(reviewCreateDTO.getUsername());
        Assertions.assertThat(foundReview.get(0).getClubName()).isEqualTo(reviewCreateDTO.getClubName());
        Assertions.assertThat(foundReview.get(0).getReviewContent()).isEqualTo(reviewCreateDTO.getReviewContent());

        Assertions.assertThatThrownBy(() -> {
            reviewService.reviewListByClubName("X");
        })
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("동아리를 찾을 수 없습니다. ClubName: X 에 해당하는 동아리 없음");
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

        Assertions.assertThatThrownBy(() -> {
            reviewService.reviewById(1000L);
        })
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("리뷰를 찾을 수 없습니다. reviewId: 1000 에 해당하는 리뷰 없음");
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

        reviewUpdateDTO.setId(1000L);
        Assertions.assertThatThrownBy(() -> {
            reviewService.updateReview(reviewUpdateDTO);
        })
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("업데이트할 리뷰를 찾을 수 없습니다. reviewId: "+reviewUpdateDTO.getId()+" 에 해당하는 리뷰 없음");
    }

    @Test
    void deleteReviewTest() {
        reviewService.newReview(reviewCreateDTO);
        List<ReviewResponseDTO> foundReviewBefore = reviewService.reviewListByClubName(testClub.getClubName());
        Assertions.assertThat(foundReviewBefore.size()).isEqualTo(1);
        Long reviewId = foundReviewBefore.get(0).getId();

        reviewService.deleteReview(reviewId);

        Assertions.assertThat(reviewService.reviewListByClubName(reviewCreateDTO.getClubName()).isEmpty()).isEqualTo(true);
        Assertions.assertThatThrownBy(() ->{
                    reviewService.reviewById(reviewId);
                })
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("리뷰를 찾을 수 없습니다. reviewId: "+reviewId+" 에 해당하는 리뷰 없음");
    }
}