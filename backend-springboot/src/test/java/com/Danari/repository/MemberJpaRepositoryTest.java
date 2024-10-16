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
    public void testMember(){
        Member member = new Member();
        member.setName("MemberA");
        member.setPassword("pwpwpwpw123");
        member.setStudentId(32190789);
        member.setUsername("ididid123");
        Member savedMember = memberJpaRepository.save(member);
    }



}