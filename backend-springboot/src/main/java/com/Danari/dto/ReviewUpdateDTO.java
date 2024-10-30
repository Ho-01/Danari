package com.Danari.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "동아리 후기 내용 수정 요청 DTO")
public class ReviewUpdateDTO {
    @Schema(description = "데이터베이스에 저장된 고유 Review(리뷰) Id")
    private Long id;
    @Schema(description = "후기 내용")
    private String reviewContent;
}
