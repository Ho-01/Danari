package com.Danari.repository;

import com.Danari.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
class MemberJpaRepositoryTest {
    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    void testMember(){
        Member member = new Member("MemberA", 32190789,"username(id)","password");
        Member savedMember = memberJpaRepository.save(member);
    }



}