package com.Danari.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberRegistrationDTO {
    private String name; // 이름
    private int studentId; // 학번
    private String username; // 아이디
    private String password; // 비밀번호
    private List<MembershipDTO> membershipDTOList;  // 동아리 정보 : 동아리 여러개 소속 가능하므로 리스트 사용
}
