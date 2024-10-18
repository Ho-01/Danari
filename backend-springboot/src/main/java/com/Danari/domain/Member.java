package com.Danari.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name; // 이름
    private int studentId; // 학번
    private String username; // 아이디
    private String password; // 비밀번호

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Membership> memberships = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> recruitmentPosts = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> eventPosts = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @Builder
    public Member(String name, int studentId, String username, String password) {
        this.name = name;
        this.studentId = studentId;
        this.username = username;
        this.password = password;
    }
}
