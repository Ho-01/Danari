package com.Danari.dto;

import com.Danari.domain.Post;
import com.Danari.domain.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "동아리 공지글/행사정보글 신규 작성 요청 DTO")
public class PostCreateDTO {
    @Schema(description = "아이디 : 누가 작성한 공지글/행사정보글인지, 아이디(로그인시 사용되는 id)로 구분")
    private String username;
    @Schema(description = "동아리 이름 : 어떤 동아리에 등록된 공지글/행사정보글인지, 동아리 이름으로 구분")
    private String clubName;
    @Schema(description = "글 제목")
    private String postTitle;
    @Schema(description = "글 내용")
    private String postContent;
    @Schema(description = "첨부 이미지 url 리스트")
    private List<String> imageUrls = new ArrayList<>();

    public static PostCreateDTO fromEntity(Post post) {
        PostCreateDTO PostCreateDTO = new PostCreateDTO();
        PostCreateDTO.setUsername(post.getAuthor().getUsername());
        PostCreateDTO.setClubName(post.getClub().getClubName());
        PostCreateDTO.setPostTitle(post.getPostTitle());
        PostCreateDTO.setPostContent(post.getPostContent());
        PostCreateDTO.setImageUrls(post.getImageUrls());
        return PostCreateDTO;
    }
}
