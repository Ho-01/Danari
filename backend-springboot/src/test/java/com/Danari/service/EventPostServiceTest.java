package com.Danari.service;

import com.Danari.domain.*;
import com.Danari.dto.PostCreateDTO;
import com.Danari.dto.PostResponseDTO;
import com.Danari.dto.PostUpdateDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class EventPostServiceTest {
    @Autowired
    private EventPostService eventPostService;
    @Autowired
    private ClubJpaRepository clubJpaRepository;
    @Autowired
    private MemberJpaRepository memberJpaRepository;
    @Autowired
    private MembershipJpaRepository membershipJpaRepository;
    private Club testClub;
    private Member testMember;
    private Membership testMembership;
    private PostCreateDTO postCreateDTO;

    @BeforeEach
    void setUp() {
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
        postCreateDTO.setPostContent("행사글 내용입니다.");
        postCreateDTO.setImageUrls(new ArrayList<>());
    }

    @Test
    void newEventPostTest() {
        eventPostService.newEventPost(postCreateDTO);

        List<PostResponseDTO> foundPost = eventPostService.eventListByClubName(testClub.getClubName());
        Assertions.assertThat(foundPost.size()).isEqualTo(1);
        Assertions.assertThat(foundPost.get(0).getUsername()).isEqualTo(testMember.getUsername());
        Assertions.assertThat(foundPost.get(0).getClubName()).isEqualTo(testClub.getClubName());
        Assertions.assertThat(foundPost.get(0).getPostTitle()).isEqualTo(postCreateDTO.getPostTitle());
        Assertions.assertThat(foundPost.get(0).getPostContent()).isEqualTo(postCreateDTO.getPostContent());
        Assertions.assertThat(foundPost.get(0).getPostType()).isEqualTo(postCreateDTO.getPostType());
    }

    @Test
    void eventListByClubNameTest() {
        eventPostService.newEventPost(postCreateDTO);

        PostResponseDTO postResponseDTO = eventPostService.eventListByClubName(testClub.getClubName()).get(0);
        PostResponseDTO foundPost = eventPostService.eventPostById(postResponseDTO.getPostId());
        Assertions.assertThat(foundPost.getUsername()).isEqualTo(testMember.getUsername());
        Assertions.assertThat(foundPost.getClubName()).isEqualTo(testClub.getClubName());
        Assertions.assertThat(foundPost.getPostTitle()).isEqualTo(postCreateDTO.getPostTitle());
        Assertions.assertThat(foundPost.getPostContent()).isEqualTo(postCreateDTO.getPostContent());
        Assertions.assertThat(foundPost.getPostType()).isEqualTo(postCreateDTO.getPostType());
    }

    @Test
    void eventPostByIdTest() {
        eventPostService.newEventPost(postCreateDTO);

        PostResponseDTO postResponseDTO = eventPostService.eventListByClubName(testClub.getClubName()).get(0);
        PostResponseDTO foundPost = eventPostService.eventPostById(postResponseDTO.getPostId());
        Assertions.assertThat(foundPost.getUsername()).isEqualTo(testMember.getUsername());
        Assertions.assertThat(foundPost.getClubName()).isEqualTo(testClub.getClubName());
        Assertions.assertThat(foundPost.getPostTitle()).isEqualTo(postCreateDTO.getPostTitle());
        Assertions.assertThat(foundPost.getPostContent()).isEqualTo(postCreateDTO.getPostContent());
        Assertions.assertThat(foundPost.getPostType()).isEqualTo(postCreateDTO.getPostType());
    }

    @Test
    void updateEventPostTest() {
        eventPostService.newEventPost(postCreateDTO);
        Long postId = eventPostService.eventListByClubName(testClub.getClubName()).get(0).getPostId();

        PostUpdateDTO postUpdateDTO = new PostUpdateDTO();
        postUpdateDTO.setPostId(postId);
        postUpdateDTO.setPostTitle("updatedTitle");
        postUpdateDTO.setPostContent("updatedContent");
        eventPostService.updateEventPost(postUpdateDTO);

        List<PostResponseDTO> foundPost = eventPostService.eventListByClubName(testClub.getClubName());
        Assertions.assertThat(foundPost.size()).isEqualTo(1);
        Assertions.assertThat(foundPost.get(0).getPostId()).isEqualTo(postId);
        Assertions.assertThat(foundPost.get(0).getPostTitle()).isEqualTo(postUpdateDTO.getPostTitle());
        Assertions.assertThat(foundPost.get(0).getPostContent()).isEqualTo(postUpdateDTO.getPostContent());
    }

    @Test
    void deleteEventPostTest() {
        eventPostService.newEventPost(postCreateDTO);

        List<PostResponseDTO> foundPostBefore = eventPostService.eventListByClubName(testClub.getClubName());
        Assertions.assertThat(foundPostBefore.size()).isEqualTo(1);

        Long postId = foundPostBefore.get(0).getPostId();
        eventPostService.deleteEventPost(postId);

        Assertions.assertThat(eventPostService.eventListByClubName(testClub.getClubName()).isEmpty()).isEqualTo(true);

        Assertions.assertThatThrownBy(() -> {
                    eventPostService.eventPostById(postId);
                })
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("행사글을 찾을 수 없습니다. postId: "+postId+" 에 해당하는 행사글 없음");
    }
}