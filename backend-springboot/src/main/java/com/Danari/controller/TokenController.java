package com.Danari.controller;

import com.Danari.dto.LoginResponseDTO;
import com.Danari.security.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/token")
public class TokenController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/refresh")
    @Operation(summary = "RefreshToken 으로 AccessToken 재발급", description = "기존 AccessToken 만료되어 사용 불가시 헤더에 RefreshToken 을 넣어 재발급 요청")
    public ResponseEntity<LoginResponseDTO> refresh(@RequestHeader("Authorization") String authorizationHeader) {
        // bearer 제거
        String refreshToken = authorizationHeader.substring(7);

        // RefreshToken에서 username 추출
        String username = jwtTokenUtil.extractUsername(refreshToken);

        // refreshToken 유효성 확인 및 accessToken 발급
        if(jwtTokenUtil.validateRefreshToken(refreshToken, username)){
            String newAccessToken = jwtTokenUtil.generateAccessToken(username);
            return ResponseEntity.ok(new LoginResponseDTO(newAccessToken, refreshToken));
        }else {
            throw new IllegalArgumentException("Refresh Token is not valid");
        }
    }
}
