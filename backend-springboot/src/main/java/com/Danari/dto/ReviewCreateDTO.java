package com.Danari.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "동아리 후기 신규 작성 요청 DTO")
public class ReviewCreateDTO {
    @Schema(description = "아이디 : 누가 작성한 리뷰인지, 아이디(로그인시 사용되는 id)로 구분")
    private String username;
    @Schema(description = "동아리 이름 : 어떤 동아리에 등록된 후기인지, 동아리 이름으로 구분")
    private String clubName;
    @Schema(description = "후기 내용")
    private String reviewContent;
}
