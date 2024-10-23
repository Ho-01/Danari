package com.Danari.service;

import com.Danari.domain.*;
import com.Danari.dto.*;
import com.Danari.repository.MemberJpaRepository;
import com.Danari.repository.MembershipJpaRepository;
import com.Danari.repository.RecruitmentPostJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RecruitmentPostServiceTest {
    @Autowired
    RecruitmentPostService recruitmentPostService;
    @Autowired
    MemberService memberService;
    @Autowired
    ClubService clubService;
    @Autowired
    MemberJpaRepository memberJpaRepository;

    private Member testMember;
    private Club testClub;
    @BeforeEach
    void setup(){
        ClubDTO clubDTO = new ClubDTO();
        clubDTO.setClubName("testClub1");
        clubDTO.setDepartment("공연예술분과");
        clubDTO.setRoomNumber("101");
        clubDTO.setDescription("testClub1 동아리입니다.");
        clubService.newClubRegister(clubDTO);

        MemberRegistrationDTO memberRegistrationDTO = new MemberRegistrationDTO();
        memberRegistrationDTO.setName("김승호");
        memberRegistrationDTO.setStudentId(32190789);
        memberRegistrationDTO.setUsername("username");
        memberRegistrationDTO.setPassword("password");
        MembershipDTO membershipDTO = new MembershipDTO();
        membershipDTO.setName("김승호");
        membershipDTO.setClubName("testClub1");
        membershipDTO.setRole(MemberGrade.PRESIDENT);
        membershipDTO.setCertificateImageUrls("urlurl");
        memberRegistrationDTO.getMembershipDTOList().add(membershipDTO);
        memberService.registerMember(memberRegistrationDTO);
    }

    @Test
    void newRecruitmentPost() {
        PostCreateDTO postCreateDTO = new PostCreateDTO("username", "testClub1", PostType.CLUB_RECRUITMENT, "postTitle", "홍보글 내용입니다.", new ArrayList<String>());
        recruitmentPostService.newRecruitmentPost(postCreateDTO);
        Optional<Member> member = memberJpaRepository.findByUsername("username");
        Post post = member.get().getRecruitmentPosts().get(0);
        PostResponseDTO foundPost = recruitmentPostService.recruitmentPostById(post.getId());

        Assertions.assertThat(foundPost.getPostContent()).isEqualTo(post.getPostContent());
    }

    @Test
    void recruitmentPostById() {
    }

    @Test
    void recruitmentListByClubName() {
    }

    @Test
    void updateRecruitmentPost() {
    }

    @Test
    void deleteRecruitmentPost() {
    }
}