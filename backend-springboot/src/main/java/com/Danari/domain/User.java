package com.Danari.domain;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; // 이름
    private int studentId; // 학번
    private String username; // 아이디
    private String password; // 비밀번호

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Membership> memberships;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> posts;

}
