package com.Danari.service;

import com.Danari.domain.Member;
import com.Danari.repository.MemberJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private MemberJpaRepository memberJpaRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username에 해당하는 Member를 데이터베이스에서 찾음
        Optional<Member> member = memberJpaRepository.findByUsername(username);
        if (!member.isPresent()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }

        // UserDetails 객체 생성
        return User.withUsername(member.get().getUsername())
                .password(member.get().getPassword()) // 암호화된 비밀번호
//                .roles("USER") // 역할 설정
                .build();
    }
}
