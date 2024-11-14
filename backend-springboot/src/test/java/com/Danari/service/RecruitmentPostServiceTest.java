package com.Danari.service;

import com.Danari.domain.*;
import com.Danari.dto.*;
import com.Danari.repository.ClubJpaRepository;
import com.Danari.repository.MemberJpaRepository;
import com.Danari.repository.MembershipJpaRepository;
import com.Danari.repository.RecruitmentPostJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    private Club testClub2;
    private Member testMember;
    private Membership testMembership;
    private PostCreateDTO postCreateDTO;
    @BeforeEach
    void setup(){
        testClub = Club.builder().clubName("testClub1").department("공연예술분과").roomNumber("101").description("testClub1 동아리입니다.").build();
        clubJpaRepository.save(testClub);
        testClub2 = Club.builder().clubName("testClub2").build();
        clubJpaRepository.save(testClub2);

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
    void newRecruitmentPostTest() {
        Assertions.assertThat(recruitmentPostService.recruitmentListByClubName(testClub.getClubName()).isEmpty()).isEqualTo(true);
        recruitmentPostService.newRecruitmentPost(postCreateDTO);
        Assertions.assertThat(recruitmentPostService.recruitmentListByClubName(testClub.getClubName()).size()).isEqualTo(1);

        postCreateDTO.setClubName("X");
        Assertions.assertThatThrownBy(() -> {
                    recruitmentPostService.newRecruitmentPost(postCreateDTO);
                })
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("동아리를 찾을 수 없습니다. ClubName: "+postCreateDTO.getClubName()+" 에 해당하는 동아리 없음");
        postCreateDTO.setClubName(testClub.getClubName());

        postCreateDTO.setUsername("X");
        Assertions.assertThatThrownBy(() -> {
                    recruitmentPostService.newRecruitmentPost(postCreateDTO);
                })
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("사용자를 찾을 수 없습니다. Username: "+postCreateDTO.getUsername()+" 에 해당하는 사용자 없음");
        postCreateDTO.setUsername(testMember.getUsername());

        postCreateDTO.setClubName(testClub2.getClubName());
        Assertions.assertThatThrownBy(() -> {
                    recruitmentPostService.newRecruitmentPost(postCreateDTO);
                })
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("동아리 가입 정보를 찾을 수 없습니다. Username: "+postCreateDTO.getUsername()+" 사용자는 ClubName : "+postCreateDTO.getClubName()+" 동아리에 가입되어 있지 않습니다.");
        postCreateDTO.setClubName(testClub.getClubName());

        Membership testMembership2 = Membership.builder().memberGrade(MemberGrade.MEMBER).build();
        testMembership2.createMembership(testMember, testClub2);
        membershipJpaRepository.save(testMembership2);
        postCreateDTO.setClubName(testClub2.getClubName());
        Assertions.assertThatThrownBy(() -> {
                    recruitmentPostService.newRecruitmentPost(postCreateDTO);
                })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("RecruitmentPost의 작성 권한은 PRESIDENT 입니다. 작성 권한이 없습니다.");
        postCreateDTO.setClubName(testClub.getClubName());
    }

    @Test
    void recruitmentListByClubNameTest() {
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

        Assertions.assertThatThrownBy(() -> {
                    recruitmentPostService.recruitmentListByClubName("X");
                })
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("동아리를 찾을 수 없습니다. ClubName: X 에 해당하는 동아리 없음");
    }

    @Test
    void recruitmentPostByIdTest() {
        recruitmentPostService.newRecruitmentPost(postCreateDTO);

        PostResponseDTO postResponseDTO = recruitmentPostService.recruitmentListByClubName(testClub.getClubName()).get(0);
        PostResponseDTO foundPost = recruitmentPostService.recruitmentPostById(postResponseDTO.getPostId());
        Assertions.assertThat(foundPost.getUsername()).isEqualTo(testMember.getUsername());
        Assertions.assertThat(foundPost.getClubName()).isEqualTo(testClub.getClubName());
        Assertions.assertThat(foundPost.getPostTitle()).isEqualTo(postCreateDTO.getPostTitle());
        Assertions.assertThat(foundPost.getPostContent()).isEqualTo(postCreateDTO.getPostContent());
        Assertions.assertThat(foundPost.getPostType()).isEqualTo(postCreateDTO.getPostType());

        Assertions.assertThatThrownBy(() -> {
                    recruitmentPostService.recruitmentPostById(1000L);
                })
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("모집글을 찾을 수 없습니다. postId: 1000 에 해당하는 모집글 없음");
    }

    @Test
    void updateRecruitmentPostTest() {
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

        postUpdateDTO.setPostId(1000L);
        Assertions.assertThatThrownBy(() -> {
                    recruitmentPostService.updateRecruitmentPost(postUpdateDTO);
                })
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("업데이트할 모집글을 찾을 수 없습니다. postId: 1000 에 해당하는 모집글 없음");
    }

    @Test
    void deleteRecruitmentPostTest() {
        recruitmentPostService.newRecruitmentPost(postCreateDTO);

        List<PostResponseDTO> foundPostBefore = recruitmentPostService.recruitmentListByClubName(testClub.getClubName());
        Assertions.assertThat(foundPostBefore.size()).isEqualTo(1);

        Long postId = foundPostBefore.get(0).getPostId();
        recruitmentPostService.deleteRecruitmentPost(postId);

        Assertions.assertThat(recruitmentPostService.recruitmentListByClubName(testClub.getClubName()).isEmpty()).isEqualTo(true);

        Assertions.assertThatThrownBy(() -> {
                    recruitmentPostService.recruitmentPostById(postId);
                })
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("모집글을 찾을 수 없습니다. postId: "+postId+" 에 해당하는 모집글 없음");

        Assertions.assertThatThrownBy(() -> {
                    recruitmentPostService.deleteRecruitmentPost(postId);
                })
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("삭제할 모집글을 찾을 수 없습니다. postId: "+postId+" 에 해당하는 모집글 없음");
    }
}