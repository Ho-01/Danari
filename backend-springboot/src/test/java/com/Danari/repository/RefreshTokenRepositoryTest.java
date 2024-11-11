package com.Danari.repository;

import com.Danari.domain.RefreshToken;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Ref;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RefreshTokenRepositoryTest {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private RefreshToken refreshToken;
    @BeforeEach
    void setUp() {
        refreshToken = RefreshToken.builder().refreshToken("tokentokentoken").username("username").expirationTime(LocalDateTime.now()).build();
        refreshTokenRepository.save(refreshToken);
    }

    @Test
    void findByUsername() {
        Optional<RefreshToken> foundRefreshToken = refreshTokenRepository.findByUsername(refreshToken.getUsername());
        Assertions.assertThat(foundRefreshToken.isPresent()).isEqualTo(true);
        Assertions.assertThat(foundRefreshToken.get().getRefreshToken()).isEqualTo(refreshToken.getRefreshToken());
        Assertions.assertThat(foundRefreshToken.get().getUsername()).isEqualTo(refreshToken.getUsername());
        Assertions.assertThat(foundRefreshToken.get().getExpirationTime()).isEqualTo(refreshToken.getExpirationTime());
    }

    @Test
    void deleteByUsername() {
        refreshTokenRepository.deleteByUsername(refreshToken.getUsername());
        Optional<RefreshToken> foundRefreshToken = refreshTokenRepository.findByUsername(refreshToken.getUsername());
        Assertions.assertThat(foundRefreshToken.isPresent()).isEqualTo(false);
    }
}