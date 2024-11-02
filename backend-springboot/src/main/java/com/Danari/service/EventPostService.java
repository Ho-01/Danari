package com.Danari.service;

import com.Danari.domain.*;

import com.Danari.dto.PostCreateDTO;
import com.Danari.dto.PostResponseDTO;
import com.Danari.dto.PostUpdateDTO;
import com.Danari.repository.ClubJpaRepository;
import com.Danari.repository.EventPostJpaRepository;
import com.Danari.repository.MemberJpaRepository;
import com.Danari.repository.MembershipJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventPostService {
    @Autowired
    private EventPostJpaRepository eventPostJpaRepository;
    @Autowired
    private ClubJpaRepository clubJpaRepository;
    @Autowired
    private MemberJpaRepository memberJpaRepository;
    @Autowired
    private MembershipJpaRepository membershipJpaRepository;

    public void newEventPost(PostCreateDTO postCreateDTO) {
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
            throw new IllegalArgumentException("EventPost의 작성 권한은 PRESIDENT 입니다. 작성 권한이 없습니다.");
        }

        Post post = Post.builder().postType(postCreateDTO.getPostType()).postContent(postCreateDTO.getPostContent()).postTitle(postCreateDTO.getPostTitle()).build();
        post.createEventPost(foundMember.get(), foundClub.get());
        eventPostJpaRepository.save(post);
    }

    public List<PostResponseDTO> eventListByClubName(String clubName) {
        Optional<Club> foundClub = clubJpaRepository.findByClubName(clubName);
        if(foundClub.isEmpty()){
            throw new IllegalArgumentException("동아리명 잘못됨, 입력된 값: "+clubName);
        }
        Club club = foundClub.get();
        return PostResponseDTO.fromEntityList(club.getEvents());
    }

    public PostResponseDTO eventPostById(Long postId) {
        Optional<Post> foundPost = eventPostJpaRepository.findById(postId);
        if(foundPost.isEmpty()){
            throw new IllegalArgumentException("postId에 해당하는 post를 찾을 수 없음.");
        }
        return PostResponseDTO.fromEntity(foundPost.get());
    }

    public void updateEventPost(PostUpdateDTO postUpdateDTO) {
        Optional<Post> foundPost = eventPostJpaRepository.findById(postUpdateDTO.getPostId());
        if(foundPost.isEmpty()){
            throw new IllegalArgumentException("postUpdateDTO의 postId 필드가 잘못되었습니다. 해당하는 post가 DB에 없습니다. postId: "+postUpdateDTO.getPostId());
        }
        Post post = foundPost.get();
        post.updatePost(postUpdateDTO.getPostTitle(), postUpdateDTO.getPostContent(), postUpdateDTO.getImageUrls());
    }

    public void deleteEventPost(Long postId) {
        eventPostJpaRepository.deleteById(postId);
    }
}
