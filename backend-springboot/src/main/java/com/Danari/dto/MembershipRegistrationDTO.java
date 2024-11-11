package com.Danari.dto;

import com.Danari.domain.MemberGrade;
import com.Danari.domain.Membership;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "동아리 가입 요청 DTO (회원가입 요청 DTO 내부에 List<>필드로 포함되어 있음)")
public class MembershipRegistrationDTO {
    @Schema(description = "멤버 이름")
    private String name;
    @Schema(description = "동아리 이름")
    private String clubName;
    @Schema(description = "동아리 내 역할 : PRESIDENT(회장) 또는 MEMBER(부원)")
    private MemberGrade role;
    @Schema(description = "동아리 가입 인증 이미지 url")
    private String certificateImageUrls;

    public static MembershipRegistrationDTO fromEntity(Membership membership){
        MembershipRegistrationDTO membershipRegistrationDTO = new MembershipRegistrationDTO();
        membershipRegistrationDTO.setName(membership.getMember().getName());
        membershipRegistrationDTO.setClubName(membership.getClub().getClubName());
        membershipRegistrationDTO.setRole(membership.getMemberGrade());
        return membershipRegistrationDTO;
    }
}
