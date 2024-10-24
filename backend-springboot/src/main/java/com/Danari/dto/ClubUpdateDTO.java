package com.Danari.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClubUpdateDTO {
    private Long id; // 동아리 id
    private String roomNumber;     // 동아리 위치
    private String description;    // 동아리 설명
}
