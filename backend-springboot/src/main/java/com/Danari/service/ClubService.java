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
            ClubDTO clubDTO = new ClubDTO();
            clubDTO.setClubName(club.getClubName());
            clubDTO.setDepartment(club.getDepartment());
            clubDTO.setRoomNumber(club.getRoomNumber());
            clubDTO.setDescription(club.getDescription());
            clubListDTO.getClubs().add(clubDTO);
        }
        return clubListDTO;
    }

    public ClubListDTO clubListByDepartment(String department) {
        ClubListDTO clubListDTO = new ClubListDTO();
        List<Club> clubList = clubJpaRepository.findByDepartment(department);
        for(Club club : clubList){
            ClubDTO clubDTO = new ClubDTO();
            clubDTO.setClubName(club.getClubName());
            clubDTO.setDepartment(club.getDepartment());
            clubDTO.setRoomNumber(club.getRoomNumber());
            clubDTO.setDescription(club.getDescription());
            clubListDTO.getClubs().add(clubDTO);
        }
        return clubListDTO;
    }

    public ClubDetailDTO clubDetailByClubName(String clubName) {
        Optional<Club> foundClub = clubJpaRepository.findByClubName(clubName);
        if(foundClub.isEmpty()){ throw new EntityNotFoundException("해당하는 동아리가 없습니다. = "+clubName); }

        Club club = foundClub.get();

        List<PostCreateDTO> eventDTOList = new ArrayList<>();
        for(Post post : club.getEvents()){
            PostCreateDTO postCreateDTO = new PostCreateDTO();
            postCreateDTO.setUsername(post.getAuthor().getUsername());
            postCreateDTO.setClubName(post.getClub().getClubName());
            postCreateDTO.setPostType(post.getPostType());
            postCreateDTO.setPostTitle(post.getPostTitle());
            postCreateDTO.setPostContent(post.getPostContent());
            postCreateDTO.setImageUrls(post.getImageUrls());

            eventDTOList.add(postCreateDTO);
        }

        List<PostCreateDTO> recruitmentDTOList = new ArrayList<>();
        for(Post post : club.getRecruitments()){
            PostCreateDTO postCreateDTO = new PostCreateDTO();
            postCreateDTO.setUsername(post.getAuthor().getUsername());
            postCreateDTO.setClubName(post.getClub().getClubName());
            postCreateDTO.setPostType(post.getPostType());
            postCreateDTO.setPostTitle(post.getPostTitle());
            postCreateDTO.setPostContent(post.getPostContent());
            postCreateDTO.setImageUrls(post.getImageUrls());
            recruitmentDTOList.add(postCreateDTO);
        }

        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        for(Review review : club.getReviews()){
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setUsername(review.getAuthor().getUsername());
            reviewDTO.setClubName(review.getClub().getClubName());
            reviewDTO.setReviewContent(review.getReviewContent());

            reviewDTOList.add(reviewDTO);
        }

        ClubDetailDTO clubDetailDTO = new ClubDetailDTO();
        clubDetailDTO.setId(club.getId());
        clubDetailDTO.setClubName(club.getClubName());
        clubDetailDTO.setDepartment(club.getDepartment());
        clubDetailDTO.setRoomNumber(club.getRoomNumber());
        clubDetailDTO.setDescription(club.getDescription());
        clubDetailDTO.setEvents(eventDTOList);
        clubDetailDTO.setRecruitments(recruitmentDTOList);
        clubDetailDTO.setReviews(reviewDTOList);
        return clubDetailDTO;
    }

    public void newClubRegister(ClubDTO clubDTO) {
        Club club = new Club(clubDTO.getClubName(), clubDTO.getRoomNumber(), clubDTO.getDepartment(), clubDTO.getDescription());
        clubJpaRepository.save(club);
    }

    public void updateClub(ClubUpdateDTO clubUpdateDTO) {

    }
}
