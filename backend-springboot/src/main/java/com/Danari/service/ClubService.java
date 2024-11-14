package com.Danari.service;

import com.Danari.domain.Club;
import com.Danari.dto.*;
import com.Danari.repository.ClubJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Club foundClub = clubJpaRepository.findByClubName(clubName)
                .orElseThrow(() -> new EntityNotFoundException("동아리를 찾을 수 없습니다. ClubName: "+clubName+" 에 해당하는 동아리 없음"));

        List<PostResponseDTO> eventDTOList = eventPostService.eventListByClubName(foundClub.getClubName());
        List<PostResponseDTO> recruitmentDTOList = recruitmentPostService.recruitmentListByClubName(foundClub.getClubName());
        List<ReviewResponseDTO> reviewResponseDTOList = reviewService.reviewListByClubName(foundClub.getClubName());

        ClubDetailDTO clubDetailDTO = ClubDetailDTO.fromEntity(foundClub);
        clubDetailDTO.setEvents(eventDTOList);
        clubDetailDTO.setRecruitments(recruitmentDTOList);
        clubDetailDTO.setReviews(reviewResponseDTOList);
        return clubDetailDTO;
    }
}
