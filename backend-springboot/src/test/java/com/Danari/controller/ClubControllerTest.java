package com.Danari.controller;

import com.Danari.domain.Club;
import com.Danari.domain.MemberGrade;
import com.Danari.dto.ClubResponseDTO;
import com.Danari.dto.LoginRequestDTO;
import com.Danari.dto.MemberRegistrationDTO;
import com.Danari.dto.MembershipRegistrationDTO;
import com.Danari.repository.ClubJpaRepository;
import com.Danari.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@Transactional
class ClubControllerTest {
    @Autowired
    private ClubController clubController;
    @Autowired
    private AuthController authController;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ClubJpaRepository clubJpaRepository;

    private LoginRequestDTO loginRequestDTO;

    @BeforeEach
    void setUp() {
        loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUserId("username");
        loginRequestDTO.setPassword("password");

        Club testClub = new Club("testClub", "101", "공연예술분과", "testClub 동아리입니다.");
        clubJpaRepository.save(testClub);

        MemberRegistrationDTO memberRegistrationDTO = new MemberRegistrationDTO();
        memberRegistrationDTO.setName("testMember");
        memberRegistrationDTO.setStudentId(32190000);
        memberRegistrationDTO.setUsername("username");
        memberRegistrationDTO.setPassword("password");

        List<MembershipRegistrationDTO> membershipRegistrationDTOList = new ArrayList<>();
        MembershipRegistrationDTO membershipRegistrationDTO = new MembershipRegistrationDTO();
        membershipRegistrationDTO.setName("testMember");
        membershipRegistrationDTO.setClubName("testClub");
        membershipRegistrationDTO.setRole(MemberGrade.MEMBER);
        membershipRegistrationDTOList.add(membershipRegistrationDTO);

        memberRegistrationDTO.setMembershipRegistrationDTOList(membershipRegistrationDTOList);
        memberService.registerMember(memberRegistrationDTO);
    }

    @Test
    void clubListAll() {
        ResponseEntity<List<ClubResponseDTO>> response = clubController.clubListAll();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().size()).isEqualTo(1);
    }

    @Test
    void clubListByDepartment() {
    }

    @Test
    void clubDetailByCLubName() {
    }
}