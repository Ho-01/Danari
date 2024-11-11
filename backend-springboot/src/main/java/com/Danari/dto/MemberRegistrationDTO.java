package com.Danari.dto;

import com.Danari.domain.Member;
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

    public static MemberRegistrationDTO fromEntity(Member member) {
        MemberRegistrationDTO memberRegistrationDTO = new MemberRegistrationDTO();
        memberRegistrationDTO.setName(member.getName());
        memberRegistrationDTO.setStudentId(member.getStudentId());
        memberRegistrationDTO.setUsername(member.getUsername());
        memberRegistrationDTO.setPassword(member.getPassword());
        return memberRegistrationDTO;
    }
}
