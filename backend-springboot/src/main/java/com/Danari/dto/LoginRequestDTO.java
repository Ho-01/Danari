package com.Danari.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDTO {
    private String userId;   // ID 필드 (아이디 입력란)
    private String password; // PW 필드 (비밀번호 입력란)
}
