package com.Danari.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReviewListDTO {
    private List<ReviewDTO> reviewDTOList = new ArrayList<>();
}
