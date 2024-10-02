package com.Danari.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ClubListDTO {
    private List<ClubDTO> clubs;   // 동아리 리스트
    public ClubListDTO() {}
    public ClubListDTO(List<ClubDTO> clubs) {
        this.clubs = clubs;
    }
}
