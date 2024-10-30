package com.Danari.dto;

import com.Danari.domain.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "동아리 모집글/행사정보글 내용 수정 요청 DTO")
public class PostUpdateDTO {
    @Schema(description = "데이터베이스에 저장된 고유 Post(게시글) Id")
    private Long postId;
    @Schema(description = "글 유형 : RECRUITMENT(모집글)인지 EVENT(공지글)인지 구분")
    private PostType postType;
    @Schema(description = "글 제목")
    private String postTitle;
    @Schema(description = "글 내용")
    private String postContent;
    @Schema(description = "첨부 이미지 url 리스트")
    private List<String> imageUrls;
}
