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
    private List<byte[]> imagesData; // 여러 이미지 데이터 (바이트 배열 리스트)
}
