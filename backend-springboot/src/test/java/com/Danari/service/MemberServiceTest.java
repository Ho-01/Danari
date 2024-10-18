package com.Danari.service;

import com.Danari.domain.Club;
import com.Danari.domain.Member;
import com.Danari.domain.MemberGrade;
import com.Danari.domain.Membership;
import com.Danari.dto.MemberRegistrationDTO;
import com.Danari.dto.MembershipDTO;
import com.Danari.repository.ClubJpaRepository;
import com.Danari.repository.MemberJpaRepository;
import com.Danari.repository.MembershipJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
        MembershipDTO testMembershipDTO1 = new MembershipDTO("김승호", "testClub1", MemberGrade.PRESIDENT, "url1");
        // testMembershipDTO2 값 설정
        MembershipDTO testMembershipDTO2 = new MembershipDTO("김승호", "testClub2", MemberGrade.MEMBER, "url2");
        // testMemberRegistrationDTO 값 설정
        testMemberRegistrationDTO = new MemberRegistrationDTO("김승호", 32190789, "username", "password");
        testMemberRegistrationDTO.getMembershipDTOList().add(testMembershipDTO1);
        testMemberRegistrationDTO.getMembershipDTOList().add(testMembershipDTO2);
    }

    @Test
    void testRegisterMember(){
        memberService.registerMember(testMemberRegistrationDTO);

        Optional<Member> savedMember = memberJpaRepository.findByUsername("username");
        Assertions.assertThat(savedMember.isPresent()).isEqualTo(true);
        Assertions.assertThat(savedMember.get().getName()).isEqualTo("김승호");
        Assertions.assertThat(savedMember.get().getStudentId()).isEqualTo(32190789);

        Optional<List<Membership>> foundMemberships = membershipJpaRepository.findByMember(savedMember.get());
        Assertions.assertThat(foundMemberships.isPresent()).isEqualTo(true);
        Assertions.assertThat(foundMemberships.get()).hasSize(2);
        Assertions.assertThat(foundMemberships.get().get(0).getMember().getName()).isEqualTo("김승호");
        Assertions.assertThat(foundMemberships.get().get(0).getClub().getClubName()).isEqualTo("testClub1");
        Assertions.assertThat(foundMemberships.get().get(1).getMember().getName()).isEqualTo("김승호");
        Assertions.assertThat(foundMemberships.get().get(1).getClub().getClubName()).isEqualTo("testClub2");

    }
}