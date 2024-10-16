package com.Danari.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Club {
    @Id @GeneratedValue
    @Column(name = "club_id")
    private Long id;
    private String clubName; // 동아리명
    private String roomNumber; // 혜당관 몇 호실인지
    private String department; // 어느 분과에 속하는 동아리인지
    // 교양분과, 봉사분과, 학술분과, 공연예술분과, 문예창작분과, 종교분과, 체육분과
    private String description; // 동아리 설명

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Post> events = new ArrayList<>(); // 동아리 행사 일정들
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Post> recruitments = new ArrayList<>(); // 동아리의 모집글들
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>(); // 동아리의 리뷰들
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Membership> memberships = new ArrayList<>(); // 동아리에 속한 회원들
}
