package com.Danari.dto;

import com.Danari.domain.MemberGrade;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MembershipResponseDTO {
    private Long id;
    private String name; // 멤버 이름
    private String clubName; // 동아리 이름
    private MemberGrade role; // PRESIDENT회장 or MEMBER부원
}
