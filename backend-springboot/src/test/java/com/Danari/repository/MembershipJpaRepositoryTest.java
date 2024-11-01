package com.Danari.repository;

import com.Danari.domain.Club;
import com.Danari.domain.Member;
import com.Danari.domain.MemberGrade;
import com.Danari.domain.Membership;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MembershipJpaRepositoryTest {
    @Autowired
    private MembershipJpaRepository membershipJpaRepository;
    @Autowired
    private ClubJpaRepository clubJpaRepository;
    @Autowired
    private MemberJpaRepository memberJpaRepository;

    private Club testClub1;
    private Club testClub2;
    private Member testMember1;
    private Member testMember2;
    private Membership testMembership1;
    private Membership testMembership2;
    private Membership testMembership3;

    @BeforeEach
    void setUp() {
        testClub1 = new Club("밴드 동아리","101","공연예술분과","밴드 동아리입니다.");
        clubJpaRepository.save(testClub1);

        testClub2 = new Club("배드민턴 동아리","202","체육분과","배드민턴 동아리입니다.");
        clubJpaRepository.save(testClub2);

        testMember1 = new Member("MemberA", 32190000, "username1","password1");
        memberJpaRepository.save(testMember1);

        testMember2 = new Member("MemberB", 32200000, "username2","password2");
        memberJpaRepository.save(testMember2);

        testMembership1 = Membership.builder().memberGrade(MemberGrade.PRESIDENT).build();
        testMembership1.createMembership(testMember1, testClub1);

        testMembership2 = Membership.builder().memberGrade(MemberGrade.MEMBER).build();
        testMembership2.createMembership(testMember1, testClub2);

        testMembership3 = Membership.builder().memberGrade(MemberGrade.MEMBER).build();
        testMembership3.createMembership(testMember2, testClub1);
    }

    @Test
    void findByMemberTest() {
        Optional<List<Membership>> foundMemberships1 = membershipJpaRepository.findByMember(testMember1);
        Assertions.assertThat(foundMemberships1.isPresent()).isEqualTo(true);
        Assertions.assertThat(foundMemberships1.get().size()).isEqualTo(2);
        Assertions.assertThat(foundMemberships1.get().get(0).getMemberGrade()).isEqualTo(testMembership1.getMemberGrade());
        Assertions.assertThat(foundMemberships1.get().get(0).getMember()).isEqualTo(testMember1);
        Assertions.assertThat(foundMemberships1.get().get(0).getClub()).isEqualTo(testClub1);
        Assertions.assertThat(foundMemberships1.get().get(1).getMemberGrade()).isEqualTo(testMembership2.getMemberGrade());
        Assertions.assertThat(foundMemberships1.get().get(1).getMember()).isEqualTo(testMember1);
        Assertions.assertThat(foundMemberships1.get().get(1).getClub()).isEqualTo(testClub2);

        Optional<List<Membership>> foundMemberships2 = membershipJpaRepository.findByMember(testMember2);
        Assertions.assertThat(foundMemberships2.isPresent()).isEqualTo(true);
        Assertions.assertThat(foundMemberships2.get().size()).isEqualTo(1);
        Assertions.assertThat(foundMemberships2.get().get(0).getMemberGrade()).isEqualTo(testMembership3.getMemberGrade());
        Assertions.assertThat(foundMemberships2.get().get(0).getMember()).isEqualTo(testMember2);
        Assertions.assertThat(foundMemberships2.get().get(0).getClub()).isEqualTo(testClub1);
    }
}
