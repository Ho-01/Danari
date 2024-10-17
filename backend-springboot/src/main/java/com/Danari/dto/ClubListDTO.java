package com.Danari.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ClubListDTO {
    private List<ClubDTO> clubs = new ArrayList<>();   // 동아리 리스트
}
