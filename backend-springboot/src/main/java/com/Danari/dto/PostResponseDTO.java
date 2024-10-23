package com.Danari.dto;

import com.Danari.domain.PostType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDTO {
    private Long postId;
    private String username; // 누가 작성한 공지글인지, 아이디로 구분
    private String clubName; // 어떤 동아리에 등록된 공지글인지, 동아리 이름으로 구분
    private PostType postType; // 모집글인지 공지글인지 구분
    private String postTitle; // 글 제목
    private String postContent; // 글 내용
    private List<String> imageUrls; // 이미지 url 리스트
}
