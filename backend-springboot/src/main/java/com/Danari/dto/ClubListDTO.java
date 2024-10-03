package com.Danari.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubListDTO {
    private List<ClubDTO> clubs;   // 동아리 리스트
    @Builder
    public ClubListDTO(List<ClubDTO> clubs) {
        this.clubs = clubs;
    }
}
