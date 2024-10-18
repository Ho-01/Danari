package com.Danari.service;

import com.Danari.domain.Club;
import com.Danari.domain.Member;
import com.Danari.domain.Post;
import com.Danari.dto.PostCreateDTO;
import com.Danari.dto.PostListDTO;
import com.Danari.dto.PostResponseDTO;
import com.Danari.dto.PostUpdateDTO;
import com.Danari.repository.ClubJpaRepository;
import com.Danari.repository.MemberJpaRepository;
import com.Danari.repository.RecruitmentPostJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecruitmentPostService {
    @Autowired
    private RecruitmentPostJpaRepository recruitmentPostJpaRepository;
    @Autowired
    private ClubJpaRepository clubJpaRepository;
    @Autowired
    private MemberJpaRepository memberJpaRepository;

    public void newRecruitmentPost(PostCreateDTO postCreateDTO) {
        Optional<Club> foundClub = clubJpaRepository.findByClubName(postCreateDTO.getClubName());
        Optional<Member> foundMember =  memberJpaRepository.findByUsername(postCreateDTO.getUsername());
        if(foundClub.isEmpty()){
            throw new IllegalArgumentException("PostCreateDTO의 ClubName 필드 잘못됨 : 존재하지 않는 동아리명입니다.");
        }
        if (foundMember.isEmpty()){
            throw new IllegalArgumentException("PostCreateDTO의 username 필드 잘못됨 : 존재하지 않는 사용자 이름입니다.");
        }

        Post post = Post.builder().postType(postCreateDTO.getPostType()).postContent(postCreateDTO.getPostContent()).postTitle(postCreateDTO.getPostTitle()).build();
        post.createRecruitmentPost(foundMember.get(), foundClub.get());
        recruitmentPostJpaRepository.save(post);
    }

    public PostResponseDTO recruitmentPostById(Long postId) {
        Optional<Post> foundPost = recruitmentPostJpaRepository.findById(postId);
        if(foundPost.isEmpty()){
            throw new IllegalArgumentException("postId에 해당하는 post를 찾을 수 없음.");
        }
        Post post = foundPost.get();
        return new PostResponseDTO(post.getId(), post.getAuthor().getUsername(), post.getClub().getClubName(), post.getPostType(), post.getPostTitle(), post.getPostContent(), post.getImageUrls());
    }

    public PostListDTO recruitmentListByClubName(String clubName) {
        PostListDTO postListDTO = new PostListDTO();
        Optional<Club> foundClub = clubJpaRepository.findByClubName(clubName);
        if(foundClub.isEmpty()){
            throw new IllegalArgumentException("동아리명 잘못됨, 입력된 값: "+clubName);
        }
        Club club = foundClub.get();
        List<Post> recruitments = club.getRecruitments();
        for(Post post : recruitments){
            PostResponseDTO postResponseDTO = new PostResponseDTO(post.getId(), post.getAuthor().getName(), post.getClub().getClubName(), post.getPostType(), post.getPostTitle(), post.getPostContent(), post.getImageUrls());
            postListDTO.getPostDTOList().add(postResponseDTO);
        }
        return postListDTO;
    }

    public void updateRecruitmentPost(PostUpdateDTO postUpdateDTO) {
        Optional<Post> foundPost = recruitmentPostJpaRepository.findById(postUpdateDTO.getPostId());
        if(foundPost.isEmpty()){
            throw new IllegalArgumentException("postUpdateDTO의 postId 필드가 잘못되었습니다. 해당하는 post가 DB에 없습니다. postId: "+postUpdateDTO.getPostId());
        }
        Post post = foundPost.get();
        post.updatePost(postUpdateDTO.getPostTitle(), postUpdateDTO.getPostContent(), postUpdateDTO.getImageUrls());
    }

    public void deleteRecruitmentPost(Long postId) {
        recruitmentPostJpaRepository.deleteById(postId);
    }
}
