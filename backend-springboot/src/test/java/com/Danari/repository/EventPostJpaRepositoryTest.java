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
class EventPostJpaRepositoryTest {
    @Autowired
    private EventPostJpaRepository eventPostJpaRepository;
    @Autowired
    private ClubJpaRepository clubJpaRepository;
    @Autowired
    private MemberJpaRepository memberJpaRepository;

    private Club testClub;
    private Member testMember;
    private Post eventPost1;
    private Post eventPost2;

    @BeforeEach
    void setUp() {
        // 동아리 생성 및 저장
        testClub = new Club("밴드 동아리","101","공연예술분과","밴드 동아리입니다.");
        clubJpaRepository.save(testClub);

        // 멤버 생성 및 저장
        testMember = new Member("김승호", 32190789, "username(ID)","password");
        memberJpaRepository.save(testMember);

        // 행사 게시물 생성 및 저장
        eventPost1 = Post.builder().postTitle("공연일정1").postContent("XX월 XX일에 예정된 공연입니다.").postType(PostType.CLUB_EVENT).build();
        eventPost1.createEventPost(testMember, testClub);
        eventPostJpaRepository.save(eventPost1);

        eventPost2 = Post.builder().postTitle("공연일정2").postContent("YY월 YY일에 예정된 공연입니다.").postType(PostType.CLUB_EVENT).build();
        eventPost2.createEventPost(testMember, testClub);
        eventPostJpaRepository.save(eventPost2);
    }

    @Test
    void testFindEventPostsByClubId() {
        List<Post> eventPosts = eventPostJpaRepository.findEventPostsByClubId(testClub.getId());

        Assertions.assertThat(eventPosts).hasSize(2);

        Assertions.assertThat(eventPosts.get(0).getClub().getId()).isEqualTo(testClub.getId());
        Assertions.assertThat(eventPosts.get(0).getClub().getClubName()).isEqualTo(testClub.getClubName());
        Assertions.assertThat(eventPosts.get(0).getAuthor().getName()).isEqualTo(testMember.getName());
        Assertions.assertThat(eventPosts.get(0).getPostTitle()).isEqualTo(eventPost1.getPostTitle());
        Assertions.assertThat(eventPosts.get(0).getPostContent()).isEqualTo(eventPost1.getPostContent());
        Assertions.assertThat(eventPosts.get(0).getPostType()).isEqualTo(eventPost1.getPostType());
        
        Assertions.assertThat(eventPosts.get(1).getClub().getId()).isEqualTo(testClub.getId());
        Assertions.assertThat(eventPosts.get(1).getClub().getClubName()).isEqualTo(testClub.getClubName());
        Assertions.assertThat(eventPosts.get(1).getAuthor().getName()).isEqualTo(testMember.getName());
        Assertions.assertThat(eventPosts.get(1).getPostTitle()).isEqualTo(eventPost2.getPostTitle());
        Assertions.assertThat(eventPosts.get(1).getPostContent()).isEqualTo(eventPost2.getPostContent());
        Assertions.assertThat(eventPosts.get(1).getPostType()).isEqualTo(eventPost2.getPostType());
    }
}
