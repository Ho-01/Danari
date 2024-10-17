package com.Danari.service;

import com.Danari.domain.Club;
import com.Danari.domain.Member;
import com.Danari.domain.Membership;
import com.Danari.dto.MemberRegistrationDTO;
import com.Danari.dto.MembershipDTO;
import com.Danari.repository.ClubJpaRepository;
import com.Danari.repository.MemberJpaRepository;
import com.Danari.repository.MembershipJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberJpaRepository memberJpaRepository;
    @Autowired
    private ClubJpaRepository clubJpaRepository;
    @Autowired
    private MembershipJpaRepository membershipJpaRepository;

    @Transactional
    public void registerMember(MemberRegistrationDTO memberRegistrationDTO){
        if(memberJpaRepository.findByUsername(memberRegistrationDTO.getUsername()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 아이디입니다");
        }

        // 멤버 생성 -> DTO로 받은 정보 입력 -> DB에 저장
        Member newMember = new Member();
        newMember.setName(memberRegistrationDTO.getName());
        newMember.setStudentId(memberRegistrationDTO.getStudentId());
        newMember.setUsername(memberRegistrationDTO.getUsername());
        newMember.setPassword(memberRegistrationDTO.getPassword());
        memberJpaRepository.save(newMember);

        // 연관관계(membership 테이블) 설정
        List<MembershipDTO> membershipDTOList =  memberRegistrationDTO.getMembershipDTOList();
        for(MembershipDTO membershipDTO : membershipDTOList){
            Membership membership = new Membership();

            membership.setMember(newMember);
            newMember.getMemberships().add(membership); // 멤버의 memberships 필드는 그냥 조회용 list이지만, 양방향으로 등록

            Club club = clubJpaRepository.findByClubName(membershipDTO.getClubName()).get();
            membership.setClub(club);
            club.getMemberships().add(membership); // 동아리의 memberships 필드는 그냥 조회용 list이지만, 양방향으로 등록

            membershipJpaRepository.save(membership);
        }
    }
}
