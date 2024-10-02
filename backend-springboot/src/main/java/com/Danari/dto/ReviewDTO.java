package com.Danari.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewDTO {
    private Long reviewId; // GeneratedValue
    private String username;
    private String reviewContent;
    private LocalDateTime createdAt;
}
