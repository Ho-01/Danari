package com.Danari.service;

import com.Danari.domain.Club;
import com.Danari.domain.Member;
import com.Danari.domain.Review;
import com.Danari.domain.Review;
import com.Danari.dto.ReviewResponseDTO;
import com.Danari.dto.ReviewUpdateDTO;
import com.Danari.dto.ReviewCreateDTO;
import com.Danari.dto.ReviewResponseDTO;
import com.Danari.repository.ClubJpaRepository;
import com.Danari.repository.MemberJpaRepository;
import com.Danari.repository.ReviewJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewJpaRepository reviewJpaRepository;
    @Autowired
    private ClubJpaRepository clubJpaRepository;
    @Autowired
    private MemberJpaRepository memberJpaRepository;

    public void newReview(ReviewCreateDTO reviewCreateDTO) {
        Optional<Club> foundClub = clubJpaRepository.findByClubName(reviewCreateDTO.getClubName());
        Optional<Member> foundMember =  memberJpaRepository.findByUsername(reviewCreateDTO.getUsername());
        if(foundClub.isEmpty()){
            throw new IllegalArgumentException("reviewCreateDTO의 ClubName 필드 잘못됨 : 존재하지 않는 동아리명입니다.");
        }
        if (foundMember.isEmpty()){
            throw new IllegalArgumentException("reviewCreateDTO의 username 필드 잘못됨 : 존재하지 않는 사용자 이름입니다.");
        }

        Review review = Review.builder().reviewContent(reviewCreateDTO.getReviewContent()).build();
        review.createReview(foundMember.get(), foundClub.get());
        reviewJpaRepository.save(review);
    }

    public List<ReviewResponseDTO> reviewListByClubName(String clubName) {
        Optional<Club> foundClub = clubJpaRepository.findByClubName(clubName);
        if(foundClub.isEmpty()){
            throw new IllegalArgumentException("동아리명 잘못됨, 입력된 값: "+clubName);
        }
        Club club = foundClub.get();
        return ReviewResponseDTO.fromEntityList(club.getReviews());
    }

    public ReviewResponseDTO reviewById(Long ReviewId) {
        Optional<Review> foundReview = reviewJpaRepository.findById(ReviewId);
        if(foundReview.isEmpty()){
            throw new IllegalArgumentException("ReviewId에 해당하는 Review를 찾을 수 없음.");
        }
        return ReviewResponseDTO.fromEntity(foundReview.get());
    }

    public void updateReview(ReviewUpdateDTO ReviewUpdateDTO) {
        Optional<Review> foundReview = reviewJpaRepository.findById(ReviewUpdateDTO.getId());
        if(foundReview.isEmpty()){
            throw new IllegalArgumentException("ReviewUpdateDTO의 id 필드가 잘못되었습니다. 해당하는 Review가 DB에 없습니다. ReviewId: "+ReviewUpdateDTO.getId());
        }
        Review review = foundReview.get();
        review.updateReview(ReviewUpdateDTO.getReviewContent());
    }

    public void deleteReview(Long reviewId) {
        reviewJpaRepository.deleteById(reviewId);
    }
}
