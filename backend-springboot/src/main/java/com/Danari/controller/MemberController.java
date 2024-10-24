package com.Danari.controller;

import com.Danari.dto.LoginRequestDTO;
import com.Danari.dto.MemberRegistrationDTO;
import com.Danari.dto.MemberResponseDTO;
import com.Danari.dto.MembershipListDTO;
import com.Danari.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/profile")
    public ResponseEntity<MemberResponseDTO> getMemberProfile(Authentication authentication){
        return ResponseEntity.ok(memberService.getMemberByUsername(authentication.getName()));
    }

    @PostMapping // "회원가입" 페이지에서 회원가입시 필요
    public ResponseEntity<String> registerMember(@RequestBody MemberRegistrationDTO memberRegistrationDTO){
        memberService.registerMember(memberRegistrationDTO);
        return ResponseEntity.ok("새 멤버 회원가입 성공");
    }
}
