package com.Danari.security;

import com.Danari.domain.RefreshToken;
import com.Danari.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenUtil {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    private final SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode("hoSecretKeyLongLongLongLongEnoughAtLeast32Bytes"));
    private final long ACCESS_TOKEN_EXPIRATION = 1000*60*15; // 15분
    private final long REFRESH_TOKEN_EXPIRATION = 1000*60*60*24*7; // 1주일

    public String generateAccessToken(String username){
        return generateToken(username, ACCESS_TOKEN_EXPIRATION);
    }

    public String generateRefreshToken(String username){
        String refreshToken = generateToken(username, REFRESH_TOKEN_EXPIRATION);

        // 기존 Refresh Token 삭제
        refreshTokenRepository.deleteByUsername(username);

        // 새 Refresh Token 저장
        RefreshToken token = RefreshToken.builder().username(username).expirationTime(LocalDateTime.now().plusDays(7)).refreshToken(refreshToken).build();
        refreshTokenRepository.save(token);

        return refreshToken;
    }

    private String generateToken(String username, Long expirationTime){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)
                .compact();
    }

    public Claims getClaims(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }

    public String extractUsername(String token){
        return getClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token){
        return getClaims(token).getExpiration().before(new Date());
    }

    public boolean isTokenValid(String token){
        return extractUsername(token) != null && !isTokenExpired(token);
    }

    public boolean validateRefreshToken(String refreshToken, String username){
        Optional<RefreshToken> foundToken = refreshTokenRepository.findByUsername(username);

        return foundToken.isPresent() && // 토큰이 존재하나?
                foundToken.get().getRefreshToken().equals(refreshToken) && // 찾아온 토큰과 기존 토큰 일치하는지 확인
                !isTokenExpired(refreshToken); // 만료되었나 확인
    }

    public void deleteRefreshToken(String username){
        refreshTokenRepository.deleteByUsername(username);
    }
}
