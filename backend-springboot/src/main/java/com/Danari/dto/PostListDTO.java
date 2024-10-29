package com.Danari.dto;

import com.Danari.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostListDTO {
    private List<PostResponseDTO> postDTOList = new ArrayList<>();

    public static PostListDTO fromEntity(List<Post> events) {
        PostListDTO postListDTO = new PostListDTO();
        for(Post event : events){
            postListDTO.getPostDTOList().add(PostResponseDTO.fromEntity(event));
        }
        return postListDTO;
    }
}
