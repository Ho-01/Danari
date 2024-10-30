package com.Danari.dto;

import com.Danari.domain.Member;
import com.Danari.domain.Membership;
import com.Danari.domain.Post;
import com.Danari.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "로그인된 사용자 정보 응답 DTO")
public class MemberResponseDTO {
    @Schema(description = "데이터베이스에 저장된 고유 사용자 id")
    private Long id;
    @Schema(description = "이름")
    private String name;
    @Schema(description = "학번")
    private int studentId;
    @Schema(description = "아이디")
    private String username;

    public static MemberResponseDTO fromEntity(Member member){
        MemberResponseDTO memberResponseDTO = new MemberResponseDTO();
        memberResponseDTO.setId(member.getId());
        memberResponseDTO.setName(member.getName());
        memberResponseDTO.setStudentId(member.getStudentId());
        memberResponseDTO.setUsername(member.getUsername());
        return memberResponseDTO;
    }
}
