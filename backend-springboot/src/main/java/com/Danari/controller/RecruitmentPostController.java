package com.Danari.controller;

import com.Danari.domain.Post;
import com.Danari.dto.PostCreateDTO;
import com.Danari.dto.PostListDTO;
import com.Danari.dto.PostResponseDTO;
import com.Danari.dto.PostUpdateDTO;
import com.Danari.service.RecruitmentPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recruitmentPosts")
public class RecruitmentPostController {
    @Autowired
    private RecruitmentPostService recruitmentPostService;

    @PostMapping // "동아리 모집 공고 등록" 페이지에서 새로운 모집공고글 등록 시 사용
    public ResponseEntity<String> newRecruitmentPost(@RequestBody PostCreateDTO postCreateDTO){
        // 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // 현재 로그인된 사용자의 username

        recruitmentPostService.newRecruitmentPost(postCreateDTO);
        return ResponseEntity.ok("모집공고글 저장 완료");
    }

    @GetMapping("/list/{clubName}") // "동아리 모집 공고 목록" 페이지에서 모집공고 목록 조회 시 사용
    public ResponseEntity<PostListDTO> recruitmentListByClubName(@PathVariable String clubName){
        PostListDTO postListDTO = new PostListDTO();
        postListDTO.setPostDTOList(recruitmentPostService.recruitmentListByClubName(clubName));
        return ResponseEntity.ok(postListDTO);
    }

    @GetMapping("/{postId}")  // "동아리 모집 공고" 페이지에서 모집공고 세부 조회 시 사용
    public ResponseEntity<PostResponseDTO> recruitmentPostById(@PathVariable Long postId){
        PostResponseDTO postResponseDTO = recruitmentPostService.recruitmentPostById(postId);
        return ResponseEntity.ok(postResponseDTO);
    }

    @PutMapping("/{postId}") // "수정 페이지" 에서 모집공고 수정 시 사용
    public ResponseEntity<String> updateRecruitmentPost(@PathVariable Long postId, @RequestBody PostUpdateDTO postUpdateDTO){
        // 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
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
    public ResponseEntity<String> deleteRecruitmentPost(@PathVariable Long postId){
        // 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
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
