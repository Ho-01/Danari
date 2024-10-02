package com.Danari.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reviewContent;
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    //getter setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
