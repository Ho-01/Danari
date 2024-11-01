package com.Danari.repository;

import com.Danari.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@DataJpaTest
class MemberJpaRepositoryTest {
    @Autowired
    MemberJpaRepository memberJpaRepository;

    private Member testMember;

    @BeforeEach
    void setUp(){
        testMember = new Member("MemberA", 32190789,"username(id)","password");
        memberJpaRepository.save(testMember);
    }

    @Test
    void findByUsernameTest(){
        Optional<Member> foundMember = memberJpaRepository.findByUsername("username(id)");

        Assertions.assertThat(foundMember.isPresent()).isEqualTo(true);

        Assertions.assertThat(foundMember.get().getName()).isEqualTo(testMember.getName());
        Assertions.assertThat(foundMember.get().getUsername()).isEqualTo(testMember.getUsername());
        Assertions.assertThat(foundMember.get().getStudentId()).isEqualTo(testMember.getStudentId());
        Assertions.assertThat(foundMember.get().getPassword()).isEqualTo(testMember.getPassword());
    }
}