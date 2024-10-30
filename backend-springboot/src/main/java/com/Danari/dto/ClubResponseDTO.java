package com.Danari.dto;

import com.Danari.domain.Club;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.lang.management.LockInfo;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "동아리 정보 응답 DTO")
public class ClubResponseDTO {
    @Schema(description = "동아리 id")
    private Long id;
    @Schema(description = "동아리 이름")
    private String clubName;
    @Schema(description = "동아리가 속한 분과")
    private String department;
    @Schema(description = "동아리방 번호(몇 호실인지)")
    private String roomNumber;
    @Schema(description = "동아리 설명")
    private String description;

    public static ClubResponseDTO fromEntity(Club club){
        ClubResponseDTO clubResponseDTO = new ClubResponseDTO();
        clubResponseDTO.setId(club.getId());
        clubResponseDTO.setClubName(club.getClubName());
        clubResponseDTO.setDepartment(club.getDepartment());
        clubResponseDTO.setRoomNumber(club.getRoomNumber());
        clubResponseDTO.setDescription(club.getDescription());
        return clubResponseDTO;
    }

    public static List<ClubResponseDTO> fromEntityList(List<Club> clubList){
        List<ClubResponseDTO> clubResponseDTOList = new ArrayList<>();
        for (Club club : clubList){
            clubResponseDTOList.add(ClubResponseDTO.fromEntity(club));
        }
        return clubResponseDTOList;
    }
}