package com.Danari.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MemberRegistrationDTO {
    private String name; // 이름
    private int studentId; // 학번
    private String username; // 아이디
    private String password; // 비밀번호
    private List<MembershipDTO> membershipDTOList = new ArrayList<>();  // 동아리 정보 : 동아리 여러개 소속 가능하므로 리스트 사용

    @Builder
    public MemberRegistrationDTO(String name, int studentId, String username, String password) {
        this.name = name;
        this.studentId = studentId;
        this.username = username;
        this.password = password;
    }
}
