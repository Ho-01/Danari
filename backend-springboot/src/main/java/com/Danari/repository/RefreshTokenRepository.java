package com.Danari.repository;

import com.Danari.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
    Optional<RefreshToken> findByUsername(String username);
    void deleteByUsername(String username);
}
