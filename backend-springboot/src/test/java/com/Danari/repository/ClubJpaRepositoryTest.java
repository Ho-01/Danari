package com.Danari.repository;

import com.Danari.domain.Club;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClubJpaRepositoryTest {
    @Autowired
    private ClubJpaRepository clubJpaRepository;
    @BeforeEach
    void setUp() {
        // 테스트 데이터 세팅
        Club club1 = new Club("배드민턴 동아리","101","체육분과","배드민턴 동아리입니다.");
        Club club2 = new Club("밴드 동아리","202","공연예술분과","밴드 동아리입니다.");
        clubJpaRepository.save(club1);
        clubJpaRepository.save(club2);
    }
    @Test
    void testFindByClubName() {
        Optional<Club> foundClubs = clubJpaRepository.findByClubName("배드민턴 동아리");
        Assertions.assertThat(foundClubs).isPresent();
        Assertions.assertThat(foundClubs.get().getRoomNumber()).isEqualTo("101");
    }

    @Test
    void testFindByDepartment() {
        List<Club> foundClubs = clubJpaRepository.findByDepartment("체육분과");
        Assertions.assertThat(foundClubs).hasSize(1);
        Assertions.assertThat(foundClubs.get(0).getRoomNumber()).isEqualTo("101");
    }

}