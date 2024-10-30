package com.Danari.dto;

import com.Danari.domain.Post;
import com.Danari.domain.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "동아리 공지글/행사정보글 내용 응답 DTO")
public class PostResponseDTO {
    @Schema(description = "데이터베이스에 저장된 고유 Post(게시글) Id")
    private Long postId;
    @Schema(description = "아이디 : 누가 작성한 공지글/행사정보글인지, 아이디(로그인시 사용되는 id)로 구분")
    private String username;
    @Schema(description = "동아리 이름 : 어떤 동아리에 등록된 공지글/행사정보글인지, 동아리 이름으로 구분")
    private String clubName;
    @Schema(description = "글 유형 : RECRUITMENT(모집글)인지 EVENT(공지글)인지 구분")
    private PostType postType;
    @Schema(description = "글 제목")
    private String postTitle;
    @Schema(description = "글 내용")
    private String postContent;
    @Schema(description = "첨부 이미지 url 리스트")
    private List<String> imageUrls;

    public static PostResponseDTO fromEntity(Post post) {
        PostResponseDTO postResponseDTO = new PostResponseDTO();
        postResponseDTO.setPostId(post.getId());
        postResponseDTO.setUsername(post.getAuthor().getUsername());
        postResponseDTO.setClubName(post.getClub().getClubName());
        postResponseDTO.setPostType(post.getPostType());
        postResponseDTO.setPostTitle(post.getPostTitle());
        postResponseDTO.setPostContent(post.getPostContent());
        postResponseDTO.setImageUrls(post.getImageUrls());
        return postResponseDTO;
    }
    
    public static List<PostResponseDTO> fromEntityList(List<Post> postList){
        List<PostResponseDTO> postResponseDTOList = new ArrayList<>();
        for(Post post : postList){
            postResponseDTOList.add(PostResponseDTO.fromEntity(post));
        }
        return postResponseDTOList;
    }
}
