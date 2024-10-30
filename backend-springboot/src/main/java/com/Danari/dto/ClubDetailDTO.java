package com.Danari.dto;

import com.Danari.domain.Club;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "동아리 세부정보 응답 DTO")
public class ClubDetailDTO {
    @Schema(description = "데이터베이스에 저장된 고유 동아리 id")
    private Long id;
    @Schema(description = "동아리 이름")
    private String clubName;
    @Schema(description = "동아리가 속한 분과")
    private String department;
    @Schema(description = "동아리방 번호(몇 호실인지)")
    private String roomNumber;
    @Schema(description = "동아리 설명")
    private String description;
    @Schema(description = "동아리 행사 리스트")
    private List<PostResponseDTO> events;
    @Schema(description = "모집 공고 리스트")
    private List<PostResponseDTO> recruitments;
    @Schema(description = "활동 후기 리스트")
    private List<ReviewResponseDTO> reviews;

    public static ClubDetailDTO fromEntity(Club club){
        ClubDetailDTO clubDetailDTO = new ClubDetailDTO();
        clubDetailDTO.setId(club.getId());
        clubDetailDTO.setClubName(club.getClubName());
        clubDetailDTO.setDepartment(club.getDepartment());
        clubDetailDTO.setRoomNumber(club.getRoomNumber());
        clubDetailDTO.setDescription(club.getDescription());
        return clubDetailDTO;
    }
}
