package com.Danari.domain;

import jakarta.persistence.*;

@Entity
public class Membership {
    @Id @GeneratedValue
    @Column(name = "membership_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @Enumerated(EnumType.STRING)
    private MemberGrade memberGrade;

    public void createMembership(Member member, Club club){
        this.member = member;
        member.getMemberships().add(this);
        this.club = club;
        club.getMemberships().add(this);
    }
}
