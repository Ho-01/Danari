package com.Danari.controller;

import com.Danari.dto.MemberRegistrationDTO;
import com.Danari.dto.MemberResponseDTO;
import com.Danari.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/profile")
    @Operation(summary = "로그인된 사용자 정보 확인", description = "클라이언트단에서 memberId, membership 등 멤버 정보가 필요할 시 사용")
    public ResponseEntity<MemberResponseDTO> getMemberProfile(Authentication authentication){
        return ResponseEntity.ok(memberService.getMemberByUsername(authentication.getName()));
    }

    @PostMapping("/registration")
    @Operation(summary = "회원가입", description = "[회원가입] 페이지에서 회원가입시 필요")
    public ResponseEntity<String> registerMember(@RequestBody MemberRegistrationDTO memberRegistrationDTO){
        memberService.registerMember(memberRegistrationDTO);
        return ResponseEntity.ok("새 멤버 회원가입 성공");
    }

}
