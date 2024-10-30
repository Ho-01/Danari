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
    @Autowired
    private ReviewService reviewService;

    public List<ClubResponseDTO> allClubList() {
        return ClubResponseDTO.fromEntityList(clubJpaRepository.findAll());
    }

    public List<ClubResponseDTO> clubListByDepartment(String department) {
        return ClubResponseDTO.fromEntityList(clubJpaRepository.findByDepartment(department));
    }

    public ClubDetailDTO clubDetailByClubName(String clubName) {
        Optional<Club> foundClub = clubJpaRepository.findByClubName(clubName);
        if(foundClub.isEmpty()){ throw new EntityNotFoundException("해당하는 동아리가 없습니다. = "+clubName); }

        Club club = foundClub.get();

        List<PostResponseDTO> eventDTOList = eventPostService.eventListByClubName(club.getClubName());
        List<PostResponseDTO> recruitmentDTOList = recruitmentPostService.recruitmentListByClubName(club.getClubName());
        List<ReviewResponseDTO> reviewResponseDTOList = reviewService.reviewListByClubName(club.getClubName());

        ClubDetailDTO clubDetailDTO = ClubDetailDTO.fromEntity(club);
        clubDetailDTO.setEvents(eventDTOList);
        clubDetailDTO.setRecruitments(recruitmentDTOList);
        clubDetailDTO.setReviews(reviewResponseDTOList);
        return clubDetailDTO;
    }
}
