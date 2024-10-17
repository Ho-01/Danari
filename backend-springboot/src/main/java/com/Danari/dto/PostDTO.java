package com.Danari.dto;

import com.Danari.domain.PostType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDTO {
    private PostType postType; // 모집글인지 공지글인지 구분
    private String postTitle; // 글 제목
    private String postContent; // 글 내용
    private LocalDateTime createdAt; // 글 생성 일시
    private List<String> imageUrls; // 이미지 url 리스트
    @Builder
    public PostDTO(PostType postType, String postTitle, String postContent, LocalDateTime createdAt, List<String> imageUrls) {
        this.postType = postType;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.createdAt = createdAt;
        this.imageUrls = imageUrls;
    }
}
