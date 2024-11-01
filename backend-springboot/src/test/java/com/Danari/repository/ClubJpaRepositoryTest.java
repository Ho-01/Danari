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

    private Club club1;
    private Club club2;

    @BeforeEach
    void setUp() {
        // 테스트 데이터 세팅
        club1 = new Club("배드민턴 동아리","101","체육분과","배드민턴 동아리입니다.");
        clubJpaRepository.save(club1);

        club2 = new Club("밴드 동아리","202","공연예술분과","밴드 동아리입니다.");
        clubJpaRepository.save(club2);
    }
    @Test
    void testFindByClubName() {
        Optional<Club> foundClub1 = clubJpaRepository.findByClubName("배드민턴 동아리");
        Assertions.assertThat(foundClub1).isPresent();
        Assertions.assertThat(foundClub1.get().getRoomNumber()).isEqualTo(club1.getRoomNumber());
        Assertions.assertThat(foundClub1.get().getDepartment()).isEqualTo(club1.getDepartment());
        Assertions.assertThat(foundClub1.get().getDescription()).isEqualTo(club1.getDescription());

        Optional<Club> foundClub2 = clubJpaRepository.findByClubName("밴드 동아리");
        Assertions.assertThat(foundClub2).isPresent();
        Assertions.assertThat(foundClub2.get().getRoomNumber()).isEqualTo(club2.getRoomNumber());
        Assertions.assertThat(foundClub2.get().getDepartment()).isEqualTo(club2.getDepartment());
        Assertions.assertThat(foundClub2.get().getDescription()).isEqualTo(club2.getDescription());
    }

    @Test
    void testFindByDepartment() {
        List<Club> foundClub1 = clubJpaRepository.findByDepartment("체육분과");
        Assertions.assertThat(foundClub1).hasSize(1);
        Assertions.assertThat(foundClub1.get(0).getRoomNumber()).isEqualTo(club1.getRoomNumber());
        Assertions.assertThat(foundClub1.get(0).getDepartment()).isEqualTo(club1.getDepartment());
        Assertions.assertThat(foundClub1.get(0).getDescription()).isEqualTo(club1.getDescription());

        List<Club> foundClub2 = clubJpaRepository.findByDepartment("공연예술분과");
        Assertions.assertThat(foundClub2).hasSize(1);
        Assertions.assertThat(foundClub2.get(0).getRoomNumber()).isEqualTo(club2.getRoomNumber());
        Assertions.assertThat(foundClub2.get(0).getDepartment()).isEqualTo(club2.getDepartment());
        Assertions.assertThat(foundClub2.get(0).getDescription()).isEqualTo(club2.getDescription());
    }

}