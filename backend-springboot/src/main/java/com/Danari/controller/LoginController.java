package com.Danari.controller;

import com.Danari.dto.LoginRequestDTO;
import com.Danari.repository.MemberJpaRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    @Operation(summary = "로그인", description = "[로그인] 페이지에서 로그인시 필요")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO){
        try {
            Authentication authentication = authenticationManager.authenticate( // 인증 시도
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getUserId(),
                            loginRequestDTO.getPassword()
                    )
            );
            // 인증 성공 후 SecurityContext에 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("로그인 성공");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 인증 실패" + e.getMessage());
        }
    }
}
