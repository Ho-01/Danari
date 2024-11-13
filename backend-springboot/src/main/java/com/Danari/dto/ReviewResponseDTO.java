package com.Danari.dto;

import com.Danari.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "동아리 후기 내용 응답 DTO")
public class ReviewResponseDTO {
    @Schema(description = "데이터베이스에 저장된 고유 Review(리뷰) Id")
    private Long id;
    @Schema(description = "아이디 : 누가 작성한 리뷰인지, 아이디(로그인시 사용되는 id)로 구분")
    private String username;
    @Schema(description = "동아리 이름 : 어떤 동아리에 등록된 후기인지, 동아리 이름으로 구분")
    private String clubName;
    @Schema(description = "후기 내용")
    private String reviewContent;
    @Schema(description = "리뷰 작성 일시")
    private LocalDateTime createdAt;

    public static ReviewResponseDTO fromEntity(Review review){
        ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO();
        reviewResponseDTO.setId(review.getId());
        reviewResponseDTO.setUsername(review.getAuthor().getUsername());
        reviewResponseDTO.setClubName(review.getClub().getClubName());
        reviewResponseDTO.setReviewContent(review.getReviewContent());
        reviewResponseDTO.setCreatedAt(review.getCreatedAt());
        return reviewResponseDTO;
    }

    public static List<ReviewResponseDTO> fromEntityList(List<Review> reviews) {
        List<ReviewResponseDTO> reviewResponseDTOList = new ArrayList<>();
        for(Review review : reviews){
            reviewResponseDTOList.add(ReviewResponseDTO.fromEntity(review));
        }
        return reviewResponseDTOList;
    }
}
