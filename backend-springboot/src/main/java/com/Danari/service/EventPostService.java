package com.Danari.service;

import com.Danari.domain.Club;
import com.Danari.domain.Post;
import com.Danari.dto.PostListDTO;
import com.Danari.dto.PostResponseDTO;
import com.Danari.repository.ClubJpaRepository;
import com.Danari.repository.EventPostJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventPostService {
    @Autowired
    private EventPostJpaRepository eventPostJpaRepository;
    @Autowired
    private ClubJpaRepository clubJpaRepository;

    public List<PostResponseDTO> eventListByClubName(String clubName) {
        Optional<Club> foundClub = clubJpaRepository.findByClubName(clubName);
        if(foundClub.isEmpty()){
            throw new IllegalArgumentException("동아리명 잘못됨, 입력된 값: "+clubName);
        }
        Club club = foundClub.get();
        return PostListDTO.fromEntity(club.getEvents()).getPostDTOList();
    }
}
