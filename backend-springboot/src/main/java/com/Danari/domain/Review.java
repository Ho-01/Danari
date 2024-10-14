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
}
