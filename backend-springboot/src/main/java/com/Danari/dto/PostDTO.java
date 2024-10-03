package com.Danari.dto;

import com.Danari.domain.PostType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDTO {
    private Long postId; // GeneratedValue
    private PostType postType;
    private String postTitle;
    private String postContent;
    private LocalDateTime createdAt;
    private List<String> imageUrls; // 이미지 url 리스트
}
