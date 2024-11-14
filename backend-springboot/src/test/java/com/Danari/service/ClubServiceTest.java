package com.Danari.service;

import com.Danari.domain.*;
import com.Danari.dto.ClubResponseDTO;
import com.Danari.dto.ClubDetailDTO;
import com.Danari.dto.PostCreateDTO;
import com.Danari.dto.ReviewCreateDTO;
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

@SpringBootTest
@Transactional
class ClubServiceTest {
    @Autowired
    private ClubService clubService;
    @Autowired
    private RecruitmentPostService recruitmentPostService;
    @Autowired
    private EventPostService eventPostService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ClubJpaRepository clubJpaRepository;
    @Autowired
    private MemberJpaRepository memberJpaRepository;
    @Autowired
    private MembershipJpaRepository membershipJpaRepository;

    private Club club1;
    private Club club2;
    private Club club3;
    private Member member;
    private Membership membership1;
    private Membership membership2;
    private Membership membership3;
    private Post recruitmentPost;
    private Post eventPost;
    private Review review;

    @BeforeEach
    void setup(){
        // 동아리 생성, 저장
        club1 = new Club("testClub1", "101", "공연예술분과", "testClub1 동아리입니다.");
        clubJpaRepository.save(club1);

        club2 = new Club("testClub2", "202", "체육분과", "testClub2 동아리입니다.");
        clubJpaRepository.save(club2);

        club3 = new Club("testClub3", "303", "체육분과", "testClub3 동아리입니다.");
        clubJpaRepository.save(club3);

        // 멤버 생성, 저장
        member = new Member("MemberA", 32190789, "username(ID)", "password");
        memberJpaRepository.save(member);

        // 멤버십 연결, 저장
        membership1 = Membership.builder().memberGrade(MemberGrade.PRESIDENT).build();
        membership1.createMembership(member, club1);
        membershipJpaRepository.save(membership1);

        membership2 = Membership.builder().memberGrade(MemberGrade.MEMBER).build();
        membership2.createMembership(member, club2);
        membershipJpaRepository.save(membership2);

        membership3 = Membership.builder().memberGrade(MemberGrade.MEMBER).build();
        membership3.createMembership(member, club3);
        membershipJpaRepository.save(membership3);

        // 홍보글 작성
        recruitmentPost = Post.builder().postTitle("recruitment").postContent("recruitment 입니다.").postType(PostType.CLUB_RECRUITMENT).author(member).club(club1).build();
        recruitmentPostService.newRecruitmentPost(PostCreateDTO.fromEntity(recruitmentPost));

        // 공지글 작성
        eventPost = Post.builder().postTitle("event").postContent("event 입니다.").postType(PostType.CLUB_EVENT).author(member).club(club1).build();
        eventPostService.newEventPost(PostCreateDTO.fromEntity(eventPost));

        // 리뷰 작성
        review = Review.builder().reviewContent("review 입니다.").author(member).club(club1).build();
        reviewService.newReview(ReviewCreateDTO.fromEntity(review));
    }

    @Test
    void allClubListTest() {
        List<ClubResponseDTO> clubResponseDTOList = clubService.allClubList();
        Assertions.assertThat(clubResponseDTOList).hasSize(3);

        Assertions.assertThat(clubResponseDTOList.get(0).getClubName()).isEqualTo(club1.getClubName());
        Assertions.assertThat(clubResponseDTOList.get(0).getRoomNumber()).isEqualTo(club1.getRoomNumber());
        Assertions.assertThat(clubResponseDTOList.get(0).getDepartment()).isEqualTo(club1.getDepartment());
        Assertions.assertThat(clubResponseDTOList.get(0).getDescription()).isEqualTo(club1.getDescription());

        Assertions.assertThat(clubResponseDTOList.get(1).getClubName()).isEqualTo(club2.getClubName());
        Assertions.assertThat(clubResponseDTOList.get(1).getRoomNumber()).isEqualTo(club2.getRoomNumber());
        Assertions.assertThat(clubResponseDTOList.get(1).getDepartment()).isEqualTo(club2.getDepartment());
        Assertions.assertThat(clubResponseDTOList.get(1).getDescription()).isEqualTo(club2.getDescription());

        Assertions.assertThat(clubResponseDTOList.get(2).getClubName()).isEqualTo(club3.getClubName());
        Assertions.assertThat(clubResponseDTOList.get(2).getRoomNumber()).isEqualTo(club3.getRoomNumber());
        Assertions.assertThat(clubResponseDTOList.get(2).getDepartment()).isEqualTo(club3.getDepartment());
        Assertions.assertThat(clubResponseDTOList.get(2).getDescription()).isEqualTo(club3.getDescription());
    }

    @Test
    void clubListByDepartmentTest() {
        List<ClubResponseDTO> clubResponseDTOList = clubService.clubListByDepartment("체육분과");
        Assertions.assertThat(clubResponseDTOList).hasSize(2);

        Assertions.assertThat(clubResponseDTOList.get(0).getClubName()).isEqualTo(club2.getClubName());
        Assertions.assertThat(clubResponseDTOList.get(0).getRoomNumber()).isEqualTo(club2.getRoomNumber());
        Assertions.assertThat(clubResponseDTOList.get(0).getDepartment()).isEqualTo(club2.getDepartment());
        Assertions.assertThat(clubResponseDTOList.get(0).getDescription()).isEqualTo(club2.getDescription());

        Assertions.assertThat(clubResponseDTOList.get(1).getClubName()).isEqualTo(club3.getClubName());
        Assertions.assertThat(clubResponseDTOList.get(1).getRoomNumber()).isEqualTo(club3.getRoomNumber());
        Assertions.assertThat(clubResponseDTOList.get(1).getDepartment()).isEqualTo(club3.getDepartment());
        Assertions.assertThat(clubResponseDTOList.get(1).getDescription()).isEqualTo(club3.getDescription());
    }

    @Test
    void clubDetailByClubNameTest() {
        ClubDetailDTO clubDetailDTO = clubService.clubDetailByClubName(club1.getClubName());

        Assertions.assertThat(clubDetailDTO.getRecruitments()).hasSize(1);
        Assertions.assertThat(clubDetailDTO.getRecruitments().get(0).getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(clubDetailDTO.getRecruitments().get(0).getClubName()).isEqualTo(club1.getClubName());
        Assertions.assertThat(clubDetailDTO.getRecruitments().get(0).getPostTitle()).isEqualTo(recruitmentPost.getPostTitle());
        Assertions.assertThat(clubDetailDTO.getRecruitments().get(0).getPostType()).isEqualTo(recruitmentPost.getPostType());
        Assertions.assertThat(clubDetailDTO.getRecruitments().get(0).getPostContent()).isEqualTo(recruitmentPost.getPostContent());

        Assertions.assertThat(clubDetailDTO.getEvents()).hasSize(1);
        Assertions.assertThat(clubDetailDTO.getEvents().get(0).getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(clubDetailDTO.getEvents().get(0).getClubName()).isEqualTo(club1.getClubName());
        Assertions.assertThat(clubDetailDTO.getEvents().get(0).getPostTitle()).isEqualTo(eventPost.getPostTitle());
        Assertions.assertThat(clubDetailDTO.getEvents().get(0).getPostType()).isEqualTo(eventPost.getPostType());
        Assertions.assertThat(clubDetailDTO.getEvents().get(0).getPostContent()).isEqualTo(eventPost.getPostContent());

        Assertions.assertThat(clubDetailDTO.getReviews()).hasSize(1);
        Assertions.assertThat(clubDetailDTO.getReviews().get(0).getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(clubDetailDTO.getReviews().get(0).getClubName()).isEqualTo(club1.getClubName());
        Assertions.assertThat(clubDetailDTO.getReviews().get(0).getReviewContent()).isEqualTo(review.getReviewContent());

        Assertions.assertThatThrownBy(() -> {
                    clubService.clubDetailByClubName("MakeError");
                })
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("동아리를 찾을 수 없습니다. ClubName: MakeError 에 해당하는 동아리 없음");
    }
}
