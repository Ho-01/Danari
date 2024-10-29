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
    @Autowired
    private RecruitmentPostService recruitmentPostService;
    @Autowired
    private EventPostService eventPostService;

    public ClubListDTO allClubList() {
        ClubListDTO clubListDTO = new ClubListDTO();
        List<Club> clubList = clubJpaRepository.findAll();
        return ClubListDTO.fromEntity(clubList);
    }

    public ClubListDTO clubListByDepartment(String department) {
        ClubListDTO clubListDTO = new ClubListDTO();
        List<Club> clubList = clubJpaRepository.findByDepartment(department);
        return ClubListDTO.fromEntity(clubList);
    }

    public ClubDetailDTO clubDetailByClubName(String clubName) {
        Optional<Club> foundClub = clubJpaRepository.findByClubName(clubName);
        if(foundClub.isEmpty()){ throw new EntityNotFoundException("해당하는 동아리가 없습니다. = "+clubName); }

        Club club = foundClub.get();

        List<PostResponseDTO> eventDTOList = eventPostService.eventListByClubName(club.getClubName());
        List<PostResponseDTO> recruitmentDTOList = recruitmentPostService.recruitmentListByClubName(club.getClubName());

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
}
