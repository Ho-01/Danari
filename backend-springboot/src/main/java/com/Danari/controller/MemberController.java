package com.Danari.controller;

import com.Danari.dto.LoginRequestDTO;
import com.Danari.dto.MemberRegistrationDTO;
import com.Danari.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/register")
    public ResponseEntity<String> registerMember(@RequestBody MemberRegistrationDTO memberRegistrationDTO){

        return ResponseEntity.ok("새 멤버 회원가입 성공");
    }
}
