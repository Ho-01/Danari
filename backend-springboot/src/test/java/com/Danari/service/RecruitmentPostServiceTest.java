package com.Danari.service;

import com.Danari.domain.*;
import com.Danari.dto.*;
import com.Danari.repository.MemberJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

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
        MembershipRegistrationDTO membershipRegistrationDTO = new MembershipRegistrationDTO();
        membershipRegistrationDTO.setName("김승호");
        membershipRegistrationDTO.setClubName("testClub1");
        membershipRegistrationDTO.setRole(MemberGrade.PRESIDENT);
        membershipRegistrationDTO.setCertificateImageUrls("urlurl");
        memberRegistrationDTO.getMembershipRegistrationDTOList().add(membershipRegistrationDTO);
        memberService.registerMember(memberRegistrationDTO);
    }

    @Test
    void newRecruitmentPost() {
        PostCreateDTO postCreateDTO = new PostCreateDTO();
        postCreateDTO.setUsername("username");
        postCreateDTO.setClubName("testClub1");
        postCreateDTO.setPostType(PostType.CLUB_RECRUITMENT);
        postCreateDTO.setPostTitle("postTitle");
        postCreateDTO.setPostContent("홍보글 내용입니다.");
        postCreateDTO.setImageUrls(new ArrayList<String>());

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