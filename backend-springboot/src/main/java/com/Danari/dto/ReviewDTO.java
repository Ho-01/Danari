package com.Danari.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewDTO {
    private Long reviewId; // GeneratedValue
    private String username; // 후기 작성자 아이디
    private String reviewContent; // 후기 내용
    private LocalDateTime createdAt; // 후기 생성일시
}
