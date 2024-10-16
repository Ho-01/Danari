package com.Danari.repository;

import com.Danari.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubJpaRepository extends JpaRepository<Club, Long> {
    List<Club> findByClubName(String clubName); // 동아리명으로 동아리 조회
    List<Club> findByDepartment(String department); // 분과별로 동아리 조회
    List<Club> findByRoomNumber(String roomNumber); // 동아리방 호실로 동아리 조회
}
