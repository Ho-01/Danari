package com.Danari.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public Membership(MemberGrade memberGrade) {
        this.memberGrade = memberGrade;
    }
}
