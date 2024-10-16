package com.Danari.service;

import com.Danari.repository.MemberJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Test
    public void testAuthentication(){

    }
}