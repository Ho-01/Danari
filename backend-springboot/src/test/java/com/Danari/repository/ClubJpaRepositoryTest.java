package com.Danari.repository;

import com.Danari.domain.Club;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClubJpaRepositoryTest {
    @Autowired
    private ClubJpaRepository clubJpaRepository;
    @BeforeEach
    void setUp() {
        // 테스트 데이터 세팅
        Club club1 = new Club();
        club1.setClubName("배드민턴 동아리");
        club1.setRoomNumber("101");
        club1.setDepartment("체육분과");
        club1.setDescription("배드민턴 동아리입니다.");

        Club club2 = new Club();
        club2.setClubName("밴드 동아리");
        club2.setRoomNumber("202");
        club2.setDepartment("공연예술분과");
        club2.setDescription("밴드 동아리입니다.");

        clubJpaRepository.save(club1);
        clubJpaRepository.save(club2);
    }
    @Test
    void testFindByClubName() {
        List<Club> foundClubs = clubJpaRepository.findByClubName("배드민턴 동아리");
        Assertions.assertThat(foundClubs).hasSize(1);
        Assertions.assertThat(foundClubs.get(0).getRoomNumber()).isEqualTo("101");
    }

    @Test
    void testFindByDepartment() {
        List<Club> foundClubs = clubJpaRepository.findByDepartment("체육분과");
        Assertions.assertThat(foundClubs).hasSize(1);
        Assertions.assertThat(foundClubs.get(0).getRoomNumber()).isEqualTo("101");
    }

    @Test
    void testFindByRoomNumber() {
        List<Club> foundClubs = clubJpaRepository.findByRoomNumber("101");
        Assertions.assertThat(foundClubs).hasSize(1);
        Assertions.assertThat(foundClubs.get(0).getClubName()).isEqualTo("배드민턴 동아리");
    }
}