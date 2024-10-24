package com.Danari.service;

import com.Danari.domain.Club;
import com.Danari.domain.Member;
import com.Danari.domain.Membership;
import com.Danari.dto.MemberRegistrationDTO;
import com.Danari.dto.MemberResponseDTO;
import com.Danari.dto.MembershipRegistrationDTO;
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
        Member newMember = new Member(memberRegistrationDTO.getName(), memberRegistrationDTO.getStudentId(), memberRegistrationDTO.getUsername(), memberRegistrationDTO.getPassword());
        memberJpaRepository.save(newMember);

        // 연관관계(membership 테이블) 설정
        List<MembershipRegistrationDTO> membershipRegistrationDTOList =  memberRegistrationDTO.getMembershipRegistrationDTOList();
        for(MembershipRegistrationDTO membershipRegistrationDTO : membershipRegistrationDTOList){
            Membership membership = Membership.builder().memberGrade(membershipRegistrationDTO.getRole()).build();
            Optional<Club> foundClub = clubJpaRepository.findByClubName(membershipRegistrationDTO.getClubName());
            if(foundClub.isEmpty()){
                throw new IllegalArgumentException("membershipDTO의 clubname과 일치하는 동아리가 없습니다");
            }
            membership.createMembership(newMember, foundClub.get());

            membershipJpaRepository.save(membership);
        }
    }

    public MemberResponseDTO getMemberByUsername(String username) {
        Optional<Member> foundMember = memberJpaRepository.findByUsername(username);
        if(foundMember.isEmpty()){
            throw new IllegalArgumentException("존재하지 않는 아이디(username)입니다 :"+username);
        }
        Member member = foundMember.get();

        MemberResponseDTO memberResponseDTO = new MemberResponseDTO();
        memberResponseDTO.setId(member.getId());
        memberResponseDTO.setName(member.getName());
        memberResponseDTO.setUsername(member.getUsername());
        memberResponseDTO.setStudentId(member.getStudentId());
        return memberResponseDTO;
    }

}
