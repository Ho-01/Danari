package com.Danari.dto;

import com.Danari.domain.PostType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PostUpdateDTO {
    private Long postId;
    private PostType postType; // 모집글인지 공지글인지 구분
    private String postTitle; // 글 제목
    private String postContent; // 글 내용
    private List<String> imageUrls; // 이미지 url 리스트

    @Builder
    public PostUpdateDTO(Long postId,  PostType postType, String postTitle, String postContent, List<String> imageUrls) {
        this.postId = postId;
        this.postType = postType;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.imageUrls = imageUrls;
    }
}
