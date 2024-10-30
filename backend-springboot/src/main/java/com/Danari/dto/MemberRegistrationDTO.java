package com.Danari.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "회원가입 요청 DTO")
public class MemberRegistrationDTO {
    @Schema(description = "이름")
    private String name;
    @Schema(description = "학번")
    private int studentId;
    @Schema(description = "아이디")
    private String username;
    @Schema(description = "비밀번호")
    private String password;
    @Schema(description = "동아리 가입 정보 : 동아리 여러개 소속 가능하므로 리스트 사용")
    private List<MembershipRegistrationDTO> membershipRegistrationDTOList = new ArrayList<>();
}
