package com.Danari.service;

import com.Danari.domain.Member;
import com.Danari.repository.MemberJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberJpaRepository memberJpaRepository;

}
