package com.Danari.controller;

import com.Danari.domain.Club;
import com.Danari.domain.MemberGrade;
import com.Danari.dto.LoginRequestDTO;
import com.Danari.dto.LoginResponseDTO;
import com.Danari.dto.MemberRegistrationDTO;
import com.Danari.dto.MembershipRegistrationDTO;
import com.Danari.repository.ClubJpaRepository;
import com.Danari.repository.RefreshTokenRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthControllerTest {
    @Autowired
    private AuthController authController;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ClubJpaRepository clubJpaRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

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
    void login() {
        ResponseEntity<LoginResponseDTO> response = authController.login(loginRequestDTO);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getAccessToken()).isNotNull();
        Assertions.assertThat(response.getBody().getRefreshToken()).isNotNull();

        loginRequestDTO.setUserId("X");
        Assertions.assertThatThrownBy(() -> {
            authController.login(loginRequestDTO);
        })
                .isInstanceOf(AuthenticationException.class)
                .hasMessageContaining("자격 증명에 실패하였습니다.");
        loginRequestDTO.setUserId("username");
    }

    @Test
    void logout() {
        String accessToken = authController.login(loginRequestDTO).getBody().getAccessToken();
        Assertions.assertThat(refreshTokenRepository.findAll().size()).isEqualTo(1);

        String authorizationHeader = "Bearer "+ accessToken;
//        ResponseEntity<String> response = authController.logout();
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Assertions.assertThat(response.getBody()).isEqualTo("로그아웃 성공");
//        Assertions.assertThat(refreshTokenRepository.findAll().isEmpty()).isEqualTo(true);
    }
}