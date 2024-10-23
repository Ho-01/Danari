package com.Danari.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDTO {
    private String username; // 후기 작성자 아이디
    private String clubName; // 후기가 작성된 동아리의 이름
    private String reviewContent; // 후기 내용
}
