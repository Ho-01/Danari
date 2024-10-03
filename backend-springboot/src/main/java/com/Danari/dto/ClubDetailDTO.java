package com.Danari.dto;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubDetailDTO {
    private Long clubId; // GeneratedValue
    private String clubName;       // 동아리 이름
    private String department;     // 동아리가 속한 분과
    private String roomNumber;     // 동아리 위치
    private String description;    // 동아리 설명
    private List<PostDTO> events; // 동아리 행사 정보
    private List<PostDTO> recruitments; // 모집 공고 정보
    private List<ReviewDTO> reviews; // 활동 후기 정보
    @Builder
    public ClubDetailDTO(Long clubId, String clubName, String department, String roomNumber, String description, List<PostDTO> events, List<PostDTO> recruitments, List<ReviewDTO> reviews) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.department = department;
        this.roomNumber = roomNumber;
        this.description = description;
        this.events = events;
        this.recruitments = recruitments;
        this.reviews = reviews;
    }
}
