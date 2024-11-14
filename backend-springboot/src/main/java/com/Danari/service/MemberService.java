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
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberJpaRepository memberJpaRepository;
    @Autowired
    private ClubJpaRepository clubJpaRepository;
    @Autowired
    private MembershipJpaRepository membershipJpaRepository;



    @Transactional
    public void registerMember(MemberRegistrationDTO memberRegistrationDTO){
        if(memberJpaRepository.findByUsername(memberRegistrationDTO.getUsername()).isPresent()){
            throw new IllegalArgumentException("회원가입이 불가능합니다. Username: "+memberRegistrationDTO.getUsername()+" 은 이미 존재하는 아이디입니다");
        }

        // DTO 토대로 새 멤버 생성 -> DB에 저장
        Member newMember = new Member(memberRegistrationDTO.getName(), memberRegistrationDTO.getStudentId(), memberRegistrationDTO.getUsername(), passwordEncoder.encode(memberRegistrationDTO.getPassword()) );
        memberJpaRepository.save(newMember);

        // 연관관계(membership 테이블) 설정
        List<MembershipRegistrationDTO> membershipRegistrationDTOList =  memberRegistrationDTO.getMembershipRegistrationDTOList();
        for(MembershipRegistrationDTO membershipRegistrationDTO : membershipRegistrationDTOList){
            Club foundClub = clubJpaRepository.findByClubName(membershipRegistrationDTO.getClubName())
                    .orElseThrow(() -> new EntityNotFoundException("동아리를 찾을 수 없습니다. ClubName: "+membershipRegistrationDTO.getClubName()+" 에 해당하는 동아리 없음"));

            Membership membership = Membership.builder().memberGrade(membershipRegistrationDTO.getRole()).build();
            membership.createMembership(newMember, foundClub);

            membershipJpaRepository.save(membership);
        }
    }

    public MemberResponseDTO getMemberByUsername(String username) {
        Member foundMember = memberJpaRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다. Username: "+username+" 에 해당하는 사용자 없음"));

        return MemberResponseDTO.fromEntity(foundMember);
    }

}
