package com.Danari.controller;

import com.Danari.dto.MembershipListDTO;
import com.Danari.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/memberships")
public class MembershipController {
    @Autowired
    private MembershipService membershipService;
    @GetMapping
    public ResponseEntity<MembershipListDTO> getMemberships(Authentication authentication){
        return ResponseEntity.ok(membershipService.getMembershipListByUsername(authentication.getName()));
    }

}
