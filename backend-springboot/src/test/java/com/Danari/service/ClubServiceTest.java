package com.Danari.service;

import com.Danari.domain.Club;
import com.Danari.domain.Member;
import com.Danari.domain.MemberGrade;
import com.Danari.domain.Membership;
import com.Danari.dto.ClubDTO;
import com.Danari.dto.ClubDetailDTO;
import com.Danari.dto.ClubListDTO;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ClubServiceTest {
    @Autowired
    private ClubService clubService;
    @Autowired
    private ClubJpaRepository clubJpaRepository;
    @Autowired
    private MemberJpaRepository memberJpaRepository;
    @Autowired
    private MembershipJpaRepository membershipJpaRepository;

    @BeforeEach
    void setup(){
        // 동아리 생성, 저장
        Club club1 = new Club("testClub1", "101", "공연예술분과", "testClub1 동아리입니다.");
        Club club2 = new Club("testClub2", "202", "체육분과", "testClub2 동아리입니다.");
        Club club3 = new Club("testClub3", "303", "체육분과", "testClub3 동아리입니다.");
        clubJpaRepository.save(club1);
        clubJpaRepository.save(club2);
        clubJpaRepository.save(club3);
        // 멤버 생성, 저장
        Member member = new Member("MemberA", 32190789, "username(ID)", "password");
        memberJpaRepository.save(member);
        // 멤버십 연결, 저장
        Membership membership1 = Membership.builder().memberGrade(MemberGrade.PRESIDENT).build();
        Membership membership2 = Membership.builder().memberGrade(MemberGrade.MEMBER).build();
        Membership membership3 = Membership.builder().memberGrade(MemberGrade.MEMBER).build();
        membership1.createMembership(member, club1);
        membership2.createMembership(member, club2);
        membership3.createMembership(member, club3);
        membershipJpaRepository.save(membership1);
        membershipJpaRepository.save(membership2);
        membershipJpaRepository.save(membership3);
    }

    @Test
    void testAllClubList() {
        ClubListDTO clubListDTO = clubService.allClubList();
        List<ClubDTO> clubDTOList = clubListDTO.getClubs();
        Assertions.assertThat(clubDTOList).hasSize(3);
        Assertions.assertThat(clubDTOList.get(0).getClubName()).isEqualTo("testClub1");
        Assertions.assertThat(clubDTOList.get(1).getDescription()).isEqualTo("testClub2 동아리입니다.");
        Assertions.assertThat(clubDTOList.get(2).getDepartment()).isEqualTo("체육분과");
    }

    @Test
    void testClubListByDepartment() {
        ClubListDTO clubListDTO = clubService.clubListByDepartment("체육분과");
        List<ClubDTO> clubDTOList = clubListDTO.getClubs();
        Assertions.assertThat(clubDTOList).hasSize(2);
        Assertions.assertThat(clubDTOList.get(0).getClubName()).isEqualTo("testClub2");
        Assertions.assertThat(clubDTOList.get(1).getDescription()).isEqualTo("testClub3 동아리입니다.");
    }

    @Test
    void testClubDetailByClubName() {
        ClubDetailDTO clubDetailDTO = clubService.clubDetailByClubName("testClub1");
        Assertions.assertThat(clubDetailDTO.getClubName()).isEqualTo("testClub1");
    }

    @Test
    void testNewClubRegister(){
        ClubDTO clubDTO = new ClubDTO("testClub4", "체육분과", "404", "testClub4 동아리입니다.");
        clubService.newClubRegister(clubDTO);
        ClubDetailDTO clubDetailDTO = clubService.clubDetailByClubName("testClub4");
        Assertions.assertThat(clubDetailDTO.getClubName()).isEqualTo("testClub4");
    }
}