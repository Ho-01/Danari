package com.Danari.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "로그인 요청 DTO")
public class LoginRequestDTO {
    @Schema(description = "ID 필드 (아이디 입력란)")
    private String userId;
    @Schema(description = "PW 필드 (비밀번호 입력란)")
    private String password;
}
