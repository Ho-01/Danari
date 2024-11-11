package com.Danari.service;

import com.Danari.domain.*;
import com.Danari.dto.*;
import com.Danari.repository.ClubJpaRepository;
import com.Danari.repository.MemberJpaRepository;
import com.Danari.repository.MembershipJpaRepository;
import com.Danari.repository.RecruitmentPostJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@SpringBootTest
@Transactional
class RecruitmentPostServiceTest {
    @Autowired
    RecruitmentPostService recruitmentPostService;
    @Autowired
    RecruitmentPostJpaRepository recruitmentPostJpaRepository;
    @Autowired
    MemberJpaRepository memberJpaRepository;
    @Autowired
    ClubJpaRepository clubJpaRepository;
    @Autowired
    MembershipJpaRepository membershipJpaRepository;

    private Club testClub;
    private Member testMember;
    private Membership testMembership;
    private PostCreateDTO postCreateDTO;
    @BeforeEach
    void setup(){
        testClub = Club.builder().clubName("testClub1").department("공연예술분과").roomNumber("101").description("testClub1 동아리입니다.").build();
        clubJpaRepository.save(testClub);

        testMember = Member.builder().name("김승호").studentId(32190789).username("username").password("password").build();
        memberJpaRepository.save(testMember);

        testMembership = Membership.builder().memberGrade(MemberGrade.PRESIDENT).build();
        testMembership.createMembership(testMember, testClub);
        membershipJpaRepository.save(testMembership);

        postCreateDTO = new PostCreateDTO();
        postCreateDTO.setUsername(testMember.getUsername());
        postCreateDTO.setClubName(testClub.getClubName());
        postCreateDTO.setPostType(PostType.CLUB_RECRUITMENT);
        postCreateDTO.setPostTitle("postTitle");
        postCreateDTO.setPostContent("홍보글 내용입니다.");
        postCreateDTO.setImageUrls(new ArrayList<>());
    }

    @Test
    void newRecruitmentPost() {
        recruitmentPostService.newRecruitmentPost(postCreateDTO);

        List<PostResponseDTO> foundPost = recruitmentPostService.recruitmentListByClubName(testClub.getClubName());
        Assertions.assertThat(foundPost.size()).isEqualTo(1);
        Assertions.assertThat(foundPost.get(0).getUsername()).isEqualTo(testMember.getUsername());
        Assertions.assertThat(foundPost.get(0).getClubName()).isEqualTo(testClub.getClubName());
        Assertions.assertThat(foundPost.get(0).getPostTitle()).isEqualTo(postCreateDTO.getPostTitle());
        Assertions.assertThat(foundPost.get(0).getPostContent()).isEqualTo(postCreateDTO.getPostContent());
        Assertions.assertThat(foundPost.get(0).getPostType()).isEqualTo(postCreateDTO.getPostType());
    }

    @Test
    void recruitmentPostById() {
        recruitmentPostService.newRecruitmentPost(postCreateDTO);

        PostResponseDTO postResponseDTO = recruitmentPostService.recruitmentListByClubName(testClub.getClubName()).get(0);
        PostResponseDTO foundPost = recruitmentPostService.recruitmentPostById(postResponseDTO.getPostId());
        Assertions.assertThat(foundPost.getUsername()).isEqualTo(testMember.getUsername());
        Assertions.assertThat(foundPost.getClubName()).isEqualTo(testClub.getClubName());
        Assertions.assertThat(foundPost.getPostTitle()).isEqualTo(postCreateDTO.getPostTitle());
        Assertions.assertThat(foundPost.getPostContent()).isEqualTo(postCreateDTO.getPostContent());
        Assertions.assertThat(foundPost.getPostType()).isEqualTo(postCreateDTO.getPostType());
    }

    @Test
    void recruitmentListByClubName() {
        recruitmentPostService.newRecruitmentPost(postCreateDTO);
        recruitmentPostService.newRecruitmentPost(postCreateDTO);

        List<PostResponseDTO> foundPost = recruitmentPostService.recruitmentListByClubName(testClub.getClubName());
        Assertions.assertThat(foundPost.size()).isEqualTo(2);

        Assertions.assertThat(foundPost.get(0).getUsername()).isEqualTo(testMember.getUsername());
        Assertions.assertThat(foundPost.get(0).getClubName()).isEqualTo(testClub.getClubName());
        Assertions.assertThat(foundPost.get(0).getPostTitle()).isEqualTo(postCreateDTO.getPostTitle());
        Assertions.assertThat(foundPost.get(0).getPostContent()).isEqualTo(postCreateDTO.getPostContent());
        Assertions.assertThat(foundPost.get(0).getPostType()).isEqualTo(postCreateDTO.getPostType());

        Assertions.assertThat(foundPost.get(0).getUsername()).isEqualTo(testMember.getUsername());
        Assertions.assertThat(foundPost.get(0).getClubName()).isEqualTo(testClub.getClubName());
        Assertions.assertThat(foundPost.get(0).getPostTitle()).isEqualTo(postCreateDTO.getPostTitle());
        Assertions.assertThat(foundPost.get(0).getPostContent()).isEqualTo(postCreateDTO.getPostContent());
        Assertions.assertThat(foundPost.get(0).getPostType()).isEqualTo(postCreateDTO.getPostType());
    }

    @Test
    void updateRecruitmentPost() {
        recruitmentPostService.newRecruitmentPost(postCreateDTO);
        Long postId = recruitmentPostService.recruitmentListByClubName(testClub.getClubName()).get(0).getPostId();

        PostUpdateDTO postUpdateDTO = new PostUpdateDTO();
        postUpdateDTO.setPostId(postId);
        postUpdateDTO.setPostTitle("updatedTitle");
        postUpdateDTO.setPostContent("updatedContent");
        recruitmentPostService.updateRecruitmentPost(postUpdateDTO);

        List<PostResponseDTO> foundPost = recruitmentPostService.recruitmentListByClubName(testClub.getClubName());
        Assertions.assertThat(foundPost.size()).isEqualTo(1);
        Assertions.assertThat(foundPost.get(0).getPostId()).isEqualTo(postId);
        Assertions.assertThat(foundPost.get(0).getPostTitle()).isEqualTo(postUpdateDTO.getPostTitle());
        Assertions.assertThat(foundPost.get(0).getPostContent()).isEqualTo(postUpdateDTO.getPostContent());
    }

    @Test
    void deleteRecruitmentPost() {
        recruitmentPostService.newRecruitmentPost(postCreateDTO);

        List<PostResponseDTO> foundPostBefore = recruitmentPostService.recruitmentListByClubName(testClub.getClubName());
        Assertions.assertThat(foundPostBefore.size()).isEqualTo(1);

        Long postId = foundPostBefore.get(0).getPostId();
        recruitmentPostService.deleteRecruitmentPost(postId);

        Assertions.assertThatThrownBy(() -> {
            recruitmentPostService.recruitmentPostById(postId);
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("postId에 해당하는 post를 찾을 수 없음.");

        Assertions.assertThatThrownBy(()->{
            recruitmentPostService.recruitmentListByClubName(testClub.getClubName());
        })
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("해당 동아리에 모집공고글이 존재하지 않습니다.");
    }
}