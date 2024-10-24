package com.Danari.dto;

import com.Danari.domain.Membership;
import com.Danari.domain.Post;
import com.Danari.domain.Review;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MemberResponseDTO {
    private Long id;
    private String name; // 이름
    private int studentId; // 학번
    private String username; // 아이디
}
