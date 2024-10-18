package com.Danari.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;
    private String reviewContent;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member author;

    public void createReview(Member author, Club club){
        this.author = author;
        author.getReviews().add(this);
        this.club = club;
        club.getReviews().add(this);
    }

    public void updateReveiw(String reviewContent){
        this.reviewContent = reviewContent;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Builder
    public Review(String reviewContent) {
        this.reviewContent = reviewContent;
    }
}
