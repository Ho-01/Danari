package com.Danari.dto;

import com.Danari.domain.MemberGrade;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MembershipDTO {
    private Long membershipId; // GeneratedValue
    private String clubName; // 동아리 이름
    private MemberGrade role; // PRESIDENT회장 or MEMBER부원
    private String certificateImageUrls; // 동아리 인증 이미지 url
}
