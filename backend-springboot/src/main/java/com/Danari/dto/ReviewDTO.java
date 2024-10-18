package com.Danari.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewDTO {
    private String username; // 후기 작성자 아이디
    private String clubName; // 후기가 작성된 동아리의 이름
    private String reviewContent; // 후기 내용

    @Builder
    public ReviewDTO(String username, String clubName, String reviewContent) {
        this.clubName = clubName;
        this.username = username;
        this.reviewContent = reviewContent;
    }
}
