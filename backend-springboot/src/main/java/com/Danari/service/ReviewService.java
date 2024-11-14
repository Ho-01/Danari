package com.Danari.service;

import com.Danari.domain.*;
import com.Danari.domain.Review;
import com.Danari.dto.ReviewResponseDTO;
import com.Danari.dto.ReviewUpdateDTO;
import com.Danari.dto.ReviewCreateDTO;
import com.Danari.repository.ClubJpaRepository;
import com.Danari.repository.MemberJpaRepository;
import com.Danari.repository.MembershipJpaRepository;
import com.Danari.repository.ReviewJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewJpaRepository reviewJpaRepository;
    @Autowired
    private ClubJpaRepository clubJpaRepository;
    @Autowired
    private MemberJpaRepository memberJpaRepository;
    @Autowired
    private MembershipJpaRepository membershipJpaRepository;

    @Transactional
    public void newReview(ReviewCreateDTO reviewCreateDTO) {
        Club foundClub = clubJpaRepository.findByClubName(reviewCreateDTO.getClubName())
                .orElseThrow(() -> new EntityNotFoundException("동아리를 찾을 수 없습니다. ClubName: "+reviewCreateDTO.getClubName()+" 에 해당하는 동아리 없음"));
        Member foundMember =  memberJpaRepository.findByUsername(reviewCreateDTO.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다. Username: "+reviewCreateDTO.getUsername()+" 에 해당하는 사용자 없음"));
        Review review = Review.builder().reviewContent(reviewCreateDTO.getReviewContent()).build();
        review.createReview(foundMember, foundClub);
        reviewJpaRepository.save(review);
    }

    public List<ReviewResponseDTO> reviewListByClubName(String clubName) {
        Club foundClub = clubJpaRepository.findByClubName(clubName)
                .orElseThrow(() -> new EntityNotFoundException("동아리를 찾을 수 없습니다. ClubName: "+clubName+" 에 해당하는 동아리 없음"));
        return ReviewResponseDTO.fromEntityList(foundClub.getReviews());
    }

    public ReviewResponseDTO reviewById(Long reviewId) {
        Review foundReview = reviewJpaRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("리뷰를 찾을 수 없습니다. reviewId: "+reviewId+" 에 해당하는 리뷰 없음"));
        return ReviewResponseDTO.fromEntity(foundReview);
    }

    @Transactional
    public void updateReview(ReviewUpdateDTO reviewUpdateDTO) {
        Review foundReview = reviewJpaRepository.findById(reviewUpdateDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("업데이트할 리뷰를 찾을 수 없습니다. reviewId: "+reviewUpdateDTO.getId()+" 에 해당하는 리뷰 없음"));

        foundReview.updateReview(reviewUpdateDTO.getReviewContent());
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review foundReview = reviewJpaRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("삭제할 리뷰를 찾을 수 없습니다. reviewId: "+reviewId+" 에 해당하는 리뷰 없음"));
        Club club = foundReview.getClub();
        club.getReviews().remove(foundReview);
        Member member = foundReview.getAuthor();
        member.getReviews().remove(foundReview);
        reviewJpaRepository.deleteById(reviewId);
    }
}
