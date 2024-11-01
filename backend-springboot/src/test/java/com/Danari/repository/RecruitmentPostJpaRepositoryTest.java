package com.Danari.repository;

import com.Danari.domain.Club;
import com.Danari.domain.Member;
import com.Danari.domain.Post;
import com.Danari.domain.PostType;
import com.Danari.service.RecruitmentPostService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RecruitmentPostJpaRepositoryTest {
    @Autowired
    private RecruitmentPostJpaRepository recruitmentPostJpaRepository;
    @Autowired
    private ClubJpaRepository clubJpaRepository;
    @Autowired
    private MemberJpaRepository memberJpaRepository;

    private Club testClub;
    private Member testMember;

    private Post recruitmentPost1;

    private Post recruitmentPost2;

    @BeforeEach
    void setUp() {
        // 동아리 생성 및 저장
        testClub = new Club("밴드 동아리","101","공연예술분과","밴드 동아리입니다.");
        clubJpaRepository.save(testClub);

        // 멤버 생성 및 저장
        testMember = new Member("김승호", 32190789, "username(ID)","password");
        memberJpaRepository.save(testMember);

        // 모집 게시물 생성 및 저장
        recruitmentPost1 = Post.builder().postTitle("모집일정1").postContent("XX월 XX일에 예정된 모집입니다.").postType(PostType.CLUB_RECRUITMENT).build();
        recruitmentPost1.createRecruitmentPost(testMember, testClub);
        recruitmentPostJpaRepository.save(recruitmentPost1);

        recruitmentPost2 = Post.builder().postTitle("모집일정2").postContent("YY월 YY일에 예정된 모집입니다.").postType(PostType.CLUB_RECRUITMENT).build();
        recruitmentPost2.createRecruitmentPost(testMember, testClub);
        recruitmentPostJpaRepository.save(recruitmentPost2);
    }

    @Test
    void testFindRecruitmentPostsByClubId() {
        List<Post> recruitmentPosts = recruitmentPostJpaRepository.findRecruitmentPostsByClubId(testClub.getId());

        Assertions.assertThat(recruitmentPosts).hasSize(2);

        Assertions.assertThat(recruitmentPosts.get(0).getClub().getId()).isEqualTo(testClub.getId());
        Assertions.assertThat(recruitmentPosts.get(0).getClub().getClubName()).isEqualTo(testClub.getClubName());
        Assertions.assertThat(recruitmentPosts.get(0).getAuthor().getName()).isEqualTo(testMember.getName());
        Assertions.assertThat(recruitmentPosts.get(0).getPostTitle()).isEqualTo(recruitmentPost1.getPostTitle());
        Assertions.assertThat(recruitmentPosts.get(0).getPostContent()).isEqualTo(recruitmentPost1.getPostContent());
        Assertions.assertThat(recruitmentPosts.get(0).getPostType()).isEqualTo(recruitmentPost1.getPostType());

        Assertions.assertThat(recruitmentPosts.get(1).getClub().getId()).isEqualTo(testClub.getId());
        Assertions.assertThat(recruitmentPosts.get(1).getClub().getClubName()).isEqualTo(testClub.getClubName());
        Assertions.assertThat(recruitmentPosts.get(1).getAuthor().getName()).isEqualTo(testMember.getName());
        Assertions.assertThat(recruitmentPosts.get(1).getPostTitle()).isEqualTo(recruitmentPost2.getPostTitle());
        Assertions.assertThat(recruitmentPosts.get(1).getPostContent()).isEqualTo(recruitmentPost2.getPostContent());
        Assertions.assertThat(recruitmentPosts.get(1).getPostType()).isEqualTo(recruitmentPost2.getPostType());
    }


}