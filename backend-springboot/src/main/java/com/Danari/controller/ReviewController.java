package com.Danari.controller;

import com.Danari.dto.*;
import com.Danari.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @PostMapping
    @Operation(summary = "동아리 후기 작성", description = "[동아리 리뷰 목록] 페이지에서 새로운 후기 작성 시 사용", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseEntity<String> newReview(@RequestBody ReviewCreateDTO reviewCreateDTO, Authentication authentication){
        // 인증된 사용자 정보 가져오기
        String currentUsername = authentication.getName(); // 현재 로그인된 사용자의 username

        reviewService.newReview(reviewCreateDTO);
        return ResponseEntity.ok("후기 작성 완료");
    }

    @GetMapping("/list/{clubName}")
    @Operation(summary = "해당 동아리의 리뷰 목록 조회", description = "[동아리 리뷰 목록] 페이지에서 리뷰 목록 조회 시 사용", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseEntity<List<ReviewResponseDTO>> reviewListByClubName(@Parameter(name = "clubName", description = "리뷰 조회할 동아리명") @PathVariable("clubName") String clubName){
        return ResponseEntity.ok(reviewService.reviewListByClubName(clubName));
    }
    
    @PutMapping("/{reviewId}")
    @Operation(summary = "동아리 리뷰 내용 수정", description = "[수정 페이지] 에서 리뷰 수정 시 사용", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseEntity<String> updateReview(@Parameter(name = "reviewId", description = "내용 수정할 리뷰의 reviewId") @PathVariable("reviewId") Long reviewId, @RequestBody ReviewUpdateDTO reviewUpdateDTO, Authentication authentication){
        // 인증된 사용자 정보 가져오기
        String currentUsername = authentication.getName(); // 현재 로그인된 사용자의 username

        // 게시글 작성자 확인 > 맞지 않으면 수정불가
        ReviewResponseDTO reviewResponseDTO = reviewService.reviewById(reviewUpdateDTO.getId());
        if(!reviewResponseDTO.getUsername().equals(currentUsername)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("게시글 수정 권한이 없습니다");
        }
        // 클라이언트 측에서 권한 없는 글에 대한 게시글 수정 버튼을 비활성화하면 되지만,
        // 혹시 모를 잘못된 수정 요청에 대응하기 위해 추가 인증 과정을 거칩니다.

        reviewUpdateDTO.setId(reviewId);
        reviewService.updateReview(reviewUpdateDTO);
        return ResponseEntity.ok("리뷰 업데이트 성공");
    }


    @DeleteMapping("/{reviewId}")
    @Operation(summary = "동아리 리뷰 삭제", description = "[동아리 리뷰 목록] 페이지에서 본인이 작성한 동아리 리뷰 삭제 시 사용", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseEntity<String> deleteReview(@Parameter(name = "reviewId", description = "삭제할 리뷰의 reviewId") @PathVariable("reviewId") Long reviewId, Authentication authentication){
        String currentUsername = authentication.getName(); // 현재 로그인된 사용자의 username

        // 게시글 작성자 확인 > 맞지 않으면 삭제불가
        ReviewResponseDTO reviewResponseDTO = reviewService.reviewById(reviewId);
        if(!reviewResponseDTO.getUsername().equals(currentUsername)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("게시글 삭제 권한이 없습니다");
        }
        // 클라이언트 측에서 권한 없는 글에 대한 게시글 삭제 버튼을 비활성화하면 되지만,
        // 혹시 모를 잘못된 삭제 요청에 대응하기 위해 추가 인증 과정을 거칩니다.

        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("리뷰 삭제 완료");
    }
}
