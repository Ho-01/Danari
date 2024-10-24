package com.Danari.service;

import com.Danari.domain.Member;
import com.Danari.domain.Membership;
import com.Danari.dto.MembershipRegistrationDTO;
import com.Danari.dto.MembershipListDTO;
import com.Danari.dto.MembershipResponseDTO;
import com.Danari.repository.MemberJpaRepository;
import com.Danari.repository.MembershipJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MembershipService {
    @Autowired
    MembershipJpaRepository membershipJpaRepository;
    @Autowired
    MemberJpaRepository memberJpaRepository;

    public MembershipListDTO getMembershipListByUsername(String username) {
        Optional<Member> foundMember = memberJpaRepository.findByUsername(username);
        if(foundMember.isEmpty()){
            throw new IllegalArgumentException("존재하지 않는 아이디(username)입니다 :"+username);
        }
        Member member = foundMember.get();

        MembershipListDTO membershipListDTO = new MembershipListDTO();
        List<Membership> membershipList = member.getMemberships();
        for(Membership membership : membershipList){
            MembershipResponseDTO membershipResponseDTO = new MembershipResponseDTO();
            membershipResponseDTO.setId(membership.getId());
            membershipResponseDTO.setRole(membership.getMemberGrade());
            membershipResponseDTO.setName(membership.getMember().getName());
            membershipResponseDTO.setClubName(membership.getClub().getClubName());

            membershipListDTO.getMembershipResponseDTOList().add(membershipResponseDTO);
        }
        return membershipListDTO;
    }
}
