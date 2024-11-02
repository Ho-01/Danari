package com.Danari.service;

import com.Danari.domain.Club;
import com.Danari.domain.Member;
import com.Danari.domain.MemberGrade;
import com.Danari.domain.Membership;
import com.Danari.dto.MemberRegistrationDTO;
import com.Danari.dto.MemberResponseDTO;
import com.Danari.dto.MembershipRegistrationDTO;
import com.Danari.repository.ClubJpaRepository;
import com.Danari.repository.MemberJpaRepository;
import com.Danari.repository.MembershipJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberJpaRepository memberJpaRepository;
    @Autowired
    private ClubJpaRepository clubJpaRepository;
    @Autowired
    private MembershipJpaRepository membershipJpaRepository;

    private MemberRegistrationDTO testMemberRegistrationDTO;

    @BeforeEach
    void setup(){
        //testClub1 값 설정
        Club testClub1 = new Club("testClub1", "101", "dep1", "this is testClub1");
        clubJpaRepository.save(testClub1);
        //testClub2 값 설정
        Club testClub2 = new Club("testClub2", "202", "dep1", "this is testClub2");
        clubJpaRepository.save(testClub2);

        // testMembershipDTO1 값 설정
        MembershipRegistrationDTO testMembershipRegistrationDTO1 = new MembershipRegistrationDTO();
        testMembershipRegistrationDTO1.setName("김승호");
        testMembershipRegistrationDTO1.setClubName("testClub1");
        testMembershipRegistrationDTO1.setRole(MemberGrade.PRESIDENT);
        testMembershipRegistrationDTO1.setCertificateImageUrls("url1");

        // testMembershipDTO2 값 설정
        MembershipRegistrationDTO testMembershipRegistrationDTO2 = new MembershipRegistrationDTO();
        testMembershipRegistrationDTO2.setName("김승호");
        testMembershipRegistrationDTO2.setClubName("testClub2");
        testMembershipRegistrationDTO2.setRole(MemberGrade.MEMBER);
        testMembershipRegistrationDTO2.setCertificateImageUrls("url2");

        // testMemberRegistrationDTO 값 설정
        testMemberRegistrationDTO = new MemberRegistrationDTO();
        testMemberRegistrationDTO.setName("김승호");
        testMemberRegistrationDTO.setStudentId(32190789);
        testMemberRegistrationDTO.setUsername("username");
        testMemberRegistrationDTO.setPassword("password");
        testMemberRegistrationDTO.getMembershipRegistrationDTOList().add(testMembershipRegistrationDTO1);
        testMemberRegistrationDTO.getMembershipRegistrationDTOList().add(testMembershipRegistrationDTO2);
    }

    @Test
    void testRegisterMember(){
        memberService.registerMember(testMemberRegistrationDTO);

        Optional<Member> savedMember = memberJpaRepository.findByUsername("username");
        Assertions.assertThat(savedMember.isPresent()).isEqualTo(true);
        Assertions.assertThat(savedMember.get().getName()).isEqualTo("김승호");
        Assertions.assertThat(savedMember.get().getStudentId()).isEqualTo(32190789);

        List<Membership> foundMemberships = membershipJpaRepository.findByMember(savedMember.get());
        Assertions.assertThat(foundMemberships).hasSize(2);
        Assertions.assertThat(foundMemberships.get(0).getMember().getName()).isEqualTo("김승호");
        Assertions.assertThat(foundMemberships.get(0).getClub().getClubName()).isEqualTo("testClub1");
        Assertions.assertThat(foundMemberships.get(1).getMember().getName()).isEqualTo("김승호");
        Assertions.assertThat(foundMemberships.get(1).getClub().getClubName()).isEqualTo("testClub2");

    }

    @Test
    void testGetMemberByUsername(){
        memberService.registerMember(testMemberRegistrationDTO);
        MemberResponseDTO memberResponseDTO = memberService.getMemberByUsername("username");

        Assertions.assertThat(memberResponseDTO.getName()).isEqualTo("김승호");
    }
}