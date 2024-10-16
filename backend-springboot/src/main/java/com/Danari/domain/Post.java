package com.Danari.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
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
    @ElementCollection
    private List<String> imageUrls; // 이미지 URL 리스트

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member author;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
}
