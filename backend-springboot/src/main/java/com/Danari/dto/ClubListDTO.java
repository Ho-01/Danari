package com.Danari.dto;

import com.Danari.domain.Club;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ClubListDTO {
    private List<ClubDTO> clubs = new ArrayList<>();   // 동아리 리스트

    public static ClubListDTO fromEntity(List<Club> clubList){
        ClubListDTO clubListDTO = new ClubListDTO();
        for(Club club : clubList){
            ClubDTO clubDTO = ClubDTO.fromEntity(club);
            clubListDTO.getClubs().add(clubDTO);
        }
        return clubListDTO;
    }
}
