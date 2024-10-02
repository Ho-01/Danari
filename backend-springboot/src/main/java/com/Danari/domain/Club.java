package com.Danari.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clubName; // 동아리명
    private String roomNumber; // 혜당관 몇 호실인지
    private String department; // 어느 분과에 속하는 동아리인지
    private String description; // 동아리 설명

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Post> events; // 동아리 행사 일정들
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Post> recruitments; // 동아리의 모집글들
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private Set<Membership> memberships; // 동아리에 속한 회원들
}
