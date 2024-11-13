package com.Danari.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private PostType postType;
    private String postTitle;
    @Lob
    private String postContent;
    private LocalDateTime createdAt;
    private List<String> imageUrls = new ArrayList<>(); // 이미지 URL 리스트

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member author;

    public void createRecruitmentPost(Member author, Club club){
        this.author = author;
        author.getRecruitmentPosts().add(this);
        this.club = club;
        club.getRecruitments().add(this);
    }

    public void createEventPost(Member author, Club club){
        this.author = author;
        author.getEventPosts().add(this);
        this.club = club;
        club.getEvents().add(this);
    }

    public void updatePost(String postTitle, String postContent, List<String> imageUrls){
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.imageUrls = imageUrls;
    }

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    @Builder
    public Post(Member author, Club club, PostType postType, String postTitle, String postContent, List<String> imageUrls) {
        this.author = author;
        this.club = club;
        this.postType = postType;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.imageUrls = imageUrls;
    }
}
