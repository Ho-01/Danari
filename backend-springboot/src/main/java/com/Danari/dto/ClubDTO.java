package com.Danari.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ClubDTO {
    private Long clubId; // GeneratedValue
    private String clubName;       // 동아리 이름
    private String department;     // 동아리가 속한 분과 (예: 학술분과)
    private String roomNumber;     // 동아리 위치 (예: 혜당관 530호)
    private String description;    // 동아리 설명

    public ClubDTO() {}
    public ClubDTO(String clubName, String department, String roomNumber, String description) {
        this.clubName = clubName;
        this.department = department;
        this.roomNumber = roomNumber;
        this.description = description;
    }
}