package com.Danari.controller;

import com.Danari.dto.PostCreateDTO;
import com.Danari.dto.PostResponseDTO;
import com.Danari.dto.PostUpdateDTO;
import com.Danari.service.RecruitmentPostService;
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
@RequestMapping("/recruitment-posts")
public class RecruitmentPostController {
    @Autowired
    private RecruitmentPostService recruitmentPostService;

    @PostMapping
    @Operation(summary = "동아리 모집공고 작성", description = "[동아리 모집 공고] 페이지에서 새로운 모집공고글 등록 시 사용", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseEntity<String> newRecruitmentPost(@RequestBody PostCreateDTO postCreateDTO, Authentication authentication){
        // 인증된 사용자 정보 가져오기
        String currentUsername = authentication.getName(); // 현재 로그인된 사용자의 username

        recruitmentPostService.newRecruitmentPost(postCreateDTO);
        return ResponseEntity.ok("모집공고글 저장 완료");
    }

    @GetMapping("/list/{clubName}")
    @Operation(summary = "해당 동아리의 모집공고 목록 조회", description = "[동아리 모집 공고 목록] 페이지에서 모집공고 목록 조회 시 사용", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseEntity<List<PostResponseDTO>> recruitmentListByClubName(@Parameter(name = "clubName", description = "모집공고 조회할 동아리명") @PathVariable("clubName") String clubName){
        return ResponseEntity.ok(recruitmentPostService.recruitmentListByClubName(clubName));
    }

    @GetMapping("/{postId}")
    @Operation(summary = "동아리 모집공고 세부내용 조회", description = "[동아리 모집 공고] 페이지에서 모집공고 세부 조회 시 사용", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseEntity<PostResponseDTO> recruitmentPostById(@Parameter(name = "postId", description = "세부조회할 모집공고의 postId") @PathVariable("postId") Long postId){
        PostResponseDTO postResponseDTO = recruitmentPostService.recruitmentPostById(postId);
        return ResponseEntity.ok(postResponseDTO);
    }

    @PutMapping("/{postId}")
    @Operation(summary = "동아리 모집공고 내용 수정", description = "[수정 페이지] 에서 모집공고 수정 시 사용", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseEntity<String> updateRecruitmentPost(@Parameter(name = "postId", description = "내용 수정할 모집공고의 postId") @PathVariable("postId") Long postId, @RequestBody PostUpdateDTO postUpdateDTO, Authentication authentication){
        // 인증된 사용자 정보 가져오기
        String currentUsername = authentication.getName(); // 현재 로그인된 사용자의 username

        // 게시글 작성자 확인 > 맞지 않으면 수정불가
        PostResponseDTO postResponseDTO = recruitmentPostService.recruitmentPostById(postUpdateDTO.getPostId());
        if(!postResponseDTO.getUsername().equals(currentUsername)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("게시글 수정 권한이 없습니다");
        }
        // 클라이언트 측에서 권한 없는 글에 대한 게시글 수정 버튼을 비활성화하면 되지만,
        // 혹시 모를 잘못된 수정 요청에 대응하기 위해 추가 인증 과정을 거칩니다.

        postUpdateDTO.setPostId(postId);
        recruitmentPostService.updateRecruitmentPost(postUpdateDTO);
        return ResponseEntity.ok("모집공고 업데이트 성공");
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "동아리 모집공고 삭제", description = "[동아리 모집 공고] 페이지에서 본인이 작성한 동아리 모집공고 삭제 시 사용", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseEntity<String> deleteRecruitmentPost(@Parameter(name = "postId", description = "삭제할 모집공고의 postId") @PathVariable("postId") Long postId, Authentication authentication){
        // 인증된 사용자 정보 가져오기
        String currentUsername = authentication.getName(); // 현재 로그인된 사용자의 username

        // 게시글 작성자 확인 > 맞지 않으면 삭제불가
        PostResponseDTO postResponseDTO = recruitmentPostService.recruitmentPostById(postId);
        if(!postResponseDTO.getUsername().equals(currentUsername)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("게시글 삭제 권한이 없습니다");
        }
        // 클라이언트 측에서 권한 없는 글에 대한 게시글 삭제 버튼을 비활성화하면 되지만,
        // 혹시 모를 잘못된 삭제 요청에 대응하기 위해 추가 인증 과정을 거칩니다.

        recruitmentPostService.deleteRecruitmentPost(postId);
        return ResponseEntity.ok("모집공고 삭제 완료");
    }
}
