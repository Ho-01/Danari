package com.Danari.service;

import com.Danari.domain.*;

import com.Danari.dto.PostCreateDTO;
import com.Danari.dto.PostResponseDTO;
import com.Danari.dto.PostUpdateDTO;
import com.Danari.repository.ClubJpaRepository;
import com.Danari.repository.EventPostJpaRepository;
import com.Danari.repository.MemberJpaRepository;
import com.Danari.repository.MembershipJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Transactional
    public void newEventPost(PostCreateDTO postCreateDTO) {
        Club foundClub = clubJpaRepository.findByClubName(postCreateDTO.getClubName())
                .orElseThrow(() -> new EntityNotFoundException("동아리를 찾을 수 없습니다. ClubName: "+postCreateDTO.getClubName()+" 에 해당하는 동아리 없음"));
        Member foundMember =  memberJpaRepository.findByUsername(postCreateDTO.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다. Username: "+postCreateDTO.getUsername()+" 에 해당하는 사용자 없음"));
        Membership foundMembership = membershipJpaRepository.findByMemberAndClub(foundMember, foundClub)
                .orElseThrow(() -> new EntityNotFoundException("동아리 가입 정보를 찾을 수 없습니다. Username: "+postCreateDTO.getUsername()+" 사용자는 ClubName : "+postCreateDTO.getClubName()+" 동아리에 가입되어 있지 않습니다."));

        if(foundMembership.getMemberGrade()!= MemberGrade.PRESIDENT){
            throw new IllegalArgumentException("EventPost의 작성 권한은 PRESIDENT 입니다. 작성 권한이 없습니다.");
        }

        Post post = Post.builder().postType(PostType.CLUB_EVENT).postContent(postCreateDTO.getPostContent()).postTitle(postCreateDTO.getPostTitle()).imageUrls(postCreateDTO.getImageUrls()).build();
        post.createEventPost(foundMember, foundClub);
        eventPostJpaRepository.save(post);
    }

    public List<PostResponseDTO> eventListByClubName(String clubName) {
        Club foundClub = clubJpaRepository.findByClubName(clubName)
                .orElseThrow(() -> new EntityNotFoundException("동아리를 찾을 수 없습니다. ClubName: "+clubName+" 에 해당하는 동아리 없음"));

        return PostResponseDTO.fromEntityList(eventPostJpaRepository.findEventPostsByClubId(foundClub.getId()));
    }

    public PostResponseDTO eventPostById(Long postId) {
        Post foundPost = eventPostJpaRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("행사글을 찾을 수 없습니다. postId: "+postId+" 에 해당하는 행사글 없음"));

        return PostResponseDTO.fromEntity(foundPost);
    }

    @Transactional
    public void updateEventPost(PostUpdateDTO postUpdateDTO) {
        Post foundPost = eventPostJpaRepository.findById(postUpdateDTO.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("업데이트할 행사글을 찾을 수 없습니다. postId: "+postUpdateDTO.getPostId()+" 에 해당하는 행사글 없음"));
        foundPost.updatePost(postUpdateDTO.getPostTitle(), postUpdateDTO.getPostContent(), postUpdateDTO.getImageUrls());
    }

    @Transactional
    public void deleteEventPost(Long postId) {
        Post foundPost = eventPostJpaRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("삭제할 행사글을 찾을 수 없습니다. postId: "+postId+" 에 해당하는 행사글 없음"));
        Club club = foundPost.getClub();
        club.getEvents().remove(foundPost); // 연관된 Club 엔티티에서 행사글 제거
        Member member = foundPost.getAuthor();
        member.getEventPosts().remove(foundPost); // 연관된 Member 엔티티에서 행사글 제거

        eventPostJpaRepository.deleteById(postId);
    }
}
