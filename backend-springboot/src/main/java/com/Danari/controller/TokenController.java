package com.Danari.controller;

import com.Danari.dto.LoginResponseDTO;
import com.Danari.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestHeader("Authorization") String authorizationHeader){
        // bearer 제거
        String refreshToken = authorizationHeader.substring(7);

        // RefreshToken에서 username 추출
        String username = jwtTokenUtil.extractUsername(refreshToken);

        // refreshToken 유효성 확인 및 accessToken 발급
        if(jwtTokenUtil.validateRefreshToken(refreshToken, username)){
            String newAccessToken = jwtTokenUtil.generateAccessToken(username);
            return ResponseEntity.ok(new LoginResponseDTO(newAccessToken, refreshToken));
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token is not valid");
        }

    }
}
