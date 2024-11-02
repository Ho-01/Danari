package com.Danari.repository;

import com.Danari.domain.Club;
import com.Danari.domain.Member;
import com.Danari.domain.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MembershipJpaRepository extends JpaRepository<Membership, Long> {
    List<Membership> findByMember(Member member);
    Optional<Membership> findByMemberAndClub(Member member, Club club);
}
