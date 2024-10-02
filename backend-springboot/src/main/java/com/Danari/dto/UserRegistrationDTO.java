package com.Danari.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRegistrationDTO {
    private Long userId; // GeneratedValue
    private String name; // 1. 이름
    private int studentId; // 2. 학번
    private String username; // 3. 아이디
    private String password; // 4. 비밀번호
    private boolean isClubMember; // 5. 동아리 소속 여부
    private List<MembershipDTO> clubInfo;  // 6. 동아리 정보 : 동아리 여러개 소속 가능하므로 리스트 사용
    private List<byte[]> certificateImages; // 7. 동아리 인증 파일 : 여러 이미지 데이터 (바이트 배열 리스트)
}
