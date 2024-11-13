package com.Danari.controller;

import com.Danari.dto.LoginRequestDTO;
import com.Danari.dto.LoginResponseDTO;
import com.Danari.repository.RefreshTokenRepository;
import com.Danari.security.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "[로그인] 페이지에서 로그인시 필요")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        Authentication authentication = authenticationManager.authenticate( // 인증 시도
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUserId(),
                        loginRequestDTO.getPassword()
                )
        );
        String accessToken = jwtTokenUtil.generateAccessToken(loginRequestDTO.getUserId());
        String refreshToken = jwtTokenUtil.generateRefreshToken(loginRequestDTO.getUserId());
        return ResponseEntity.ok(new LoginResponseDTO(accessToken, refreshToken));
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "[로그아웃] 페이지에서 로그아웃시 필요, 로그아웃시 Refresh Token 삭제")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader){
        String accessToken = authorizationHeader.substring(7);
        if(jwtTokenUtil.isTokenValid(accessToken)){
            String username = jwtTokenUtil.extractUsername(accessToken);
            refreshTokenRepository.deleteByUsername(username);
            return ResponseEntity.ok("logout success");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid token");
    }
}
