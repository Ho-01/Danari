package com.Danari.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MembershipListDTO {
    private List<MembershipResponseDTO> membershipResponseDTOList = new ArrayList<>();
}
