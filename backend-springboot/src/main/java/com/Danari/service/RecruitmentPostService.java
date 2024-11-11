package com.Danari.service;

import com.Danari.domain.*;
import com.Danari.dto.PostCreateDTO;
import com.Danari.dto.PostResponseDTO;
import com.Danari.dto.PostUpdateDTO;
import com.Danari.repository.ClubJpaRepository;
import com.Danari.repository.MemberJpaRepository;
import com.Danari.repository.MembershipJpaRepository;
import com.Danari.repository.RecruitmentPostJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RecruitmentPostService {
    @Autowired
    private RecruitmentPostJpaRepository recruitmentPostJpaRepository;
    @Autowired
    private ClubJpaRepository clubJpaRepository;
    @Autowired
    private MemberJpaRepository memberJpaRepository;
    @Autowired
    private MembershipJpaRepository membershipJpaRepository;

    public void newRecruitmentPost(PostCreateDTO postCreateDTO) {
        Optional<Club> foundClub = clubJpaRepository.findByClubName(postCreateDTO.getClubName());
        Optional<Member> foundMember =  memberJpaRepository.findByUsername(postCreateDTO.getUsername());
        if(foundClub.isEmpty()){
            throw new IllegalArgumentException("PostCreateDTO의 ClubName 필드 잘못됨 : 존재하지 않는 동아리명입니다.");
        }
        if (foundMember.isEmpty()){
            throw new IllegalArgumentException("PostCreateDTO의 username 필드 잘못됨 : 존재하지 않는 사용자 이름입니다.");
        }
        Optional<Membership> foundMembership = membershipJpaRepository.findByMemberAndClub(foundMember.get(), foundClub.get());
        if(foundMembership.isEmpty()){
            throw new IllegalArgumentException("PostCreateDTO의 username: "+postCreateDTO.getUsername()+" 에 해당하는 사용자는 ClubName : "+postCreateDTO.getClubName()+"에 해당하는 동아리에 가입되어 있지 않습니다.");
        }
        if(foundMembership.get().getMemberGrade()!= MemberGrade.PRESIDENT){
            throw new IllegalArgumentException("RecruitmentPost의 작성 권한은 PRESIDENT 입니다. 작성 권한이 없습니다.");
        }

        Post post = Post.builder().postType(postCreateDTO.getPostType()).postContent(postCreateDTO.getPostContent()).postTitle(postCreateDTO.getPostTitle()).build();
        post.createRecruitmentPost(foundMember.get(), foundClub.get());
        recruitmentPostJpaRepository.save(post);
    }

    public List<PostResponseDTO> recruitmentListByClubName(String clubName) {

        Optional<Club> foundClub = clubJpaRepository.findByClubName(clubName);
        if(foundClub.isEmpty()){
            throw new IllegalArgumentException("동아리명 잘못됨, 입력된 값: "+clubName);
        }
        Club club = foundClub.get();
        if(club.getRecruitments().isEmpty()){
            throw new NoSuchElementException("해당 동아리에 모집공고글이 존재하지 않습니다.");
        }
        return PostResponseDTO.fromEntityList(club.getRecruitments());
    }

    public PostResponseDTO recruitmentPostById(Long postId) {
        Optional<Post> foundPost = recruitmentPostJpaRepository.findById(postId);
        if(foundPost.isEmpty()){
            throw new IllegalArgumentException("postId에 해당하는 post를 찾을 수 없음.");
        }
        return PostResponseDTO.fromEntity(foundPost.get());
    }

    public void updateRecruitmentPost(PostUpdateDTO postUpdateDTO) {
        Optional<Post> foundPost = recruitmentPostJpaRepository.findById(postUpdateDTO.getPostId());
        if(foundPost.isEmpty()){
            throw new IllegalArgumentException("postUpdateDTO의 postId 필드가 잘못되었습니다. 해당하는 post가 DB에 없습니다. postId: "+postUpdateDTO.getPostId());
        }
        Post post = foundPost.get();
        post.updatePost(postUpdateDTO.getPostTitle(), postUpdateDTO.getPostContent(), postUpdateDTO.getImageUrls());
    }

    @Transactional
    public void deleteRecruitmentPost(Long postId) {
        Optional<Post> foundPost = recruitmentPostJpaRepository.findById(postId);
        if(foundPost.isEmpty()){
            throw new IllegalArgumentException("이미 삭제된 글이거나, id가 잘못되었습니다");
        }
        // 연관된 Club 엔티티에서 모집글 제거
        Club club = foundPost.get().getClub();
        club.getRecruitments().remove(foundPost.get());

        recruitmentPostJpaRepository.deleteById(postId);
    }
}
