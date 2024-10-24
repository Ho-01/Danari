package com.Danari.dto;

import com.Danari.domain.MemberGrade;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class MembershipRegistrationDTO {
    private Long id;
    private String name; // 멤버 이름
    private String clubName; // 동아리 이름
    private MemberGrade role; // PRESIDENT회장 or MEMBER부원
    private String certificateImageUrls; // 동아리 인증 이미지 url
}
