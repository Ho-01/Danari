package com.Danari.repository;

import com.Danari.domain.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipJpaRepository extends JpaRepository<Membership, Long> {
}
