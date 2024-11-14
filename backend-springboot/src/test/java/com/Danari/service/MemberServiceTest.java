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
import jakarta.persistence.EntityNotFoundException;
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

    private Club testClub1;
    private Club testClub2;
    private MembershipRegistrationDTO membershipRegistrationDTO1;
    private MembershipRegistrationDTO membershipRegistrationDTO2;
    private MemberRegistrationDTO testMemberRegistrationDTO;

    @BeforeEach
    void setup(){
        //testClub1 값 설정
        testClub1 = new Club("testClub1", "101", "dep1", "this is testClub1");
        clubJpaRepository.save(testClub1);
        //testClub2 값 설정
        testClub2 = new Club("testClub2", "202", "dep1", "this is testClub2");
        clubJpaRepository.save(testClub2);

        // membershipRegistrationDTO1 값 설정
        membershipRegistrationDTO1 = new MembershipRegistrationDTO();
        membershipRegistrationDTO1.setName("김승호");
        membershipRegistrationDTO1.setClubName("testClub1");
        membershipRegistrationDTO1.setRole(MemberGrade.PRESIDENT);

        // membershipRegistrationDTO2 값 설정
        membershipRegistrationDTO2 = new MembershipRegistrationDTO();
        membershipRegistrationDTO2.setName("김승호");
        membershipRegistrationDTO2.setClubName("testClub2");
        membershipRegistrationDTO2.setRole(MemberGrade.MEMBER);

        // testMemberRegistrationDTO 값 설정
        testMemberRegistrationDTO = new MemberRegistrationDTO();
        testMemberRegistrationDTO.setName("김승호");
        testMemberRegistrationDTO.setStudentId(32190789);
        testMemberRegistrationDTO.setUsername("username");
        testMemberRegistrationDTO.setPassword("password");
        testMemberRegistrationDTO.getMembershipRegistrationDTOList().add(membershipRegistrationDTO1);
        testMemberRegistrationDTO.getMembershipRegistrationDTOList().add(membershipRegistrationDTO2);
    }

    @Test
    void registerMemberTest(){
        memberService.registerMember(testMemberRegistrationDTO);

        Optional<Member> foundMember = memberJpaRepository.findByUsername(testMemberRegistrationDTO.getUsername());
        Assertions.assertThat(foundMember.isPresent()).isEqualTo(true);
        Assertions.assertThat(foundMember.get().getName()).isEqualTo(testMemberRegistrationDTO.getName());
        Assertions.assertThat(foundMember.get().getStudentId()).isEqualTo(testMemberRegistrationDTO.getStudentId());

        List<Membership> foundMembership = membershipJpaRepository.findByMember(foundMember.get());
        Assertions.assertThat(foundMembership.size()).isEqualTo(testMemberRegistrationDTO.getMembershipRegistrationDTOList().size());
        Assertions.assertThat(foundMembership.get(0).getMember()).isEqualTo(foundMember.get());
        Assertions.assertThat(foundMembership.get(0).getClub()).isEqualTo(testClub1);
        Assertions.assertThat(foundMembership.get(0).getMemberGrade()).isEqualTo(membershipRegistrationDTO1.getRole());
        Assertions.assertThat(foundMembership.get(1).getMember()).isEqualTo(foundMember.get());
        Assertions.assertThat(foundMembership.get(1).getClub()).isEqualTo(testClub2);
        Assertions.assertThat(foundMembership.get(1).getMemberGrade()).isEqualTo(membershipRegistrationDTO2.getRole());

        Assertions.assertThatThrownBy(() -> {
                    memberService.registerMember(testMemberRegistrationDTO);
                })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("회원가입이 불가능합니다. Username: "+testMemberRegistrationDTO.getUsername()+" 은 이미 존재하는 아이디입니다");

        testMemberRegistrationDTO.getMembershipRegistrationDTOList().get(0).setClubName("X");
        testMemberRegistrationDTO.setUsername("newUsername");
        Assertions.assertThatThrownBy(() -> {
                    memberService.registerMember(testMemberRegistrationDTO);
                })
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("동아리를 찾을 수 없습니다. ClubName: "+testMemberRegistrationDTO.getMembershipRegistrationDTOList().get(0).getClubName()+" 에 해당하는 동아리 없음");
        testMemberRegistrationDTO.getMembershipRegistrationDTOList().get(0).setClubName("testClub1");

    }

    @Test
    void getMemberByUsernameTest(){
        memberService.registerMember(testMemberRegistrationDTO);
        MemberResponseDTO memberResponseDTO = memberService.getMemberByUsername(testMemberRegistrationDTO.getUsername());
        Assertions.assertThat(memberResponseDTO.getName()).isEqualTo(testMemberRegistrationDTO.getName());
        Assertions.assertThat(memberResponseDTO.getUsername()).isEqualTo(testMemberRegistrationDTO.getUsername());
        Assertions.assertThat(memberResponseDTO.getStudentId()).isEqualTo(testMemberRegistrationDTO.getStudentId());

        Assertions.assertThatThrownBy(() -> {
            memberService.getMemberByUsername("X");
        })
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("사용자를 찾을 수 없습니다. Username: X 에 해당하는 사용자 없음");
    }
}