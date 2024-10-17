package com.Danari.service;

import com.Danari.domain.Club;
import com.Danari.domain.Post;
import com.Danari.domain.Review;
import com.Danari.dto.*;
import com.Danari.repository.ClubJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClubService {
    @Autowired
    private ClubJpaRepository clubJpaRepository;

    public ClubListDTO allClubList() {
        ClubListDTO clubListDTO = new ClubListDTO();
        List<Club> clubList = clubJpaRepository.findAll();
        for(Club club : clubList){
            ClubDTO clubDTO = new ClubDTO(club.getClubName(), club.getDepartment(), club.getRoomNumber(), club.getDescription());
            clubListDTO.getClubs().add(clubDTO);
        }
        return clubListDTO;
    }

    public ClubListDTO clubListByDepartment(String department) {
        ClubListDTO clubListDTO = new ClubListDTO();
        List<Club> clubList = clubJpaRepository.findByDepartment(department);
        for(Club club : clubList){
            ClubDTO clubDTO = new ClubDTO(club.getClubName(), club.getDepartment(), club.getRoomNumber(), club.getDescription());
            clubListDTO.getClubs().add(clubDTO);
        }
        return clubListDTO;
    }

    public ClubDetailDTO clubDetailByClubName(String clubName) {
        Optional<Club> foundClub = clubJpaRepository.findByClubName(clubName);
        if(foundClub.isPresent()){
            Club club = foundClub.get();

            List<PostDTO> eventDTOList = new ArrayList<>();
            for(Post post : club.getEvents()){
                PostDTO postDTO = new PostDTO(post.getPostType(), post.getPostTitle(), post.getPostContent(), post.getCreatedAt(), post.getImageUrls());
                eventDTOList.add(postDTO);
            }

            List<PostDTO> recruitmentDTOList = new ArrayList<>();
            for(Post post : club.getRecruitments()){
                PostDTO postDTO = new PostDTO(post.getPostType(), post.getPostTitle(), post.getPostContent(), post.getCreatedAt(), post.getImageUrls());
                recruitmentDTOList.add(postDTO);
            }

            List<ReviewDTO> reviewDTOList = new ArrayList<>();
            for(Review review : club.getReviews()){
                ReviewDTO reviewDTO = new ReviewDTO(review.getAuthor().getUsername(), review.getReviewContent(), review.getCreatedAt());
                reviewDTOList.add(reviewDTO);
            }

            return new ClubDetailDTO(club.getClubName(), club.getDepartment(), club.getRoomNumber(), club.getDescription(), eventDTOList, recruitmentDTOList, reviewDTOList);
        } else {
            throw new EntityNotFoundException("해당하는 동아리가 없습니다. = "+clubName);
        }
    }
}
