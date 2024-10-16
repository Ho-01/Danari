package com.Danari.repository;

import com.Danari.domain.Club;
import com.Danari.domain.Member;
import com.Danari.domain.Post;
import com.Danari.domain.PostType;
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
    private EventPostJpaRepository eventPostJpaRepository;
    @Autowired
    private ClubJpaRepository clubJpaRepository;
    @Autowired
    private MemberJpaRepository memberJpaRepository;

    private Club testClub;
    private Member testMember;

    @BeforeEach
    void setUp() {
        // 동아리 생성 및 저장
        testClub = new Club();
        testClub.setClubName("밴드 동아리");
        testClub.setRoomNumber("101");
        testClub.setDepartment("공연예술분과");
        testClub.setDescription("밴드 동아리입니다.");
        clubJpaRepository.save(testClub);

        // 멤버 생성 및 저장
        testMember = new Member();
        memberJpaRepository.save(testMember);

        // 행사 게시물 생성 및 저장
        Post eventPost1 = new Post();
        testMember.getPosts().add(eventPost1);
        eventPost1.setAuthor(testMember);
        eventPost1.setPostType(PostType.CLUB_RECRUITMENT);
        eventPost1.setPostTitle("모집일정1");
        eventPost1.setPostContent("XX월 XX일에 모집 예정입니다.");
        eventPost1.setClub(testClub);
        eventPostJpaRepository.save(eventPost1);

        Post eventPost2 = new Post();
        testMember.getPosts().add(eventPost2);
        eventPost2.setAuthor(testMember);
        eventPost2.setPostType(PostType.CLUB_RECRUITMENT);
        eventPost2.setPostTitle("모집일정2");
        eventPost2.setPostContent("YY월 YY일에 모집 예정입니다.");
        eventPost2.setClub(testClub);
        eventPostJpaRepository.save(eventPost2);
    }

    @Test
    void testFindRecruitmentPostsByClubId() {
        // 테스트 클럽 ID로 모집 글 찾기
        List<Post> eventPosts = eventPostJpaRepository.findEventPostsByClubId(testClub.getId());

        // 검증: 생성된 두 개의 모집글이 올바르게 조회되었는지 확인
        Assertions.assertThat(eventPosts).hasSize(2);
        Assertions.assertThat(eventPosts.get(0).getClub().getId()).isEqualTo(testClub.getId());
        Assertions.assertThat(eventPosts.get(1).getClub().getId()).isEqualTo(testClub.getId());
    }
}