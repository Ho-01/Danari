package com.Danari.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "로그인 응답 DTO")
public class LoginResponseDTO {
    @Schema(description = "accessToken 필드 (만료기한 : 15분)")
    private String accessToken;
    @Schema(description = "refreshToken 필드 (만료기한 : 7일)")
    private String refreshToken;
}
