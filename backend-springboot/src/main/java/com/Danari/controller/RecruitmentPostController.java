package com.Danari.controller;

import com.Danari.dto.PostCreateDTO;
import com.Danari.dto.PostListDTO;
import com.Danari.dto.PostResponseDTO;
import com.Danari.dto.PostUpdateDTO;
import com.Danari.service.RecruitmentPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recruitmentPost")
public class RecruitmentPostController {
    @Autowired
    private RecruitmentPostService recruitmentPostService;

    @PostMapping("/create")
    public ResponseEntity<String> newRecruitmentPost(@RequestBody PostCreateDTO postCreateDTO){
        recruitmentPostService.newRecruitmentPost(postCreateDTO);
        return ResponseEntity.ok("모집공고글 저장 완료");
    }

    @GetMapping("/read")
    public ResponseEntity<PostResponseDTO> recruitmentPostById(@RequestParam Long postId){
        PostResponseDTO postResponseDTO = recruitmentPostService.recruitmentPostById(postId);
        return ResponseEntity.ok(postResponseDTO);
    }

    @GetMapping("/readList")
    public ResponseEntity<PostListDTO> recruitmentListByClubName(@RequestParam String clubName){
        PostListDTO postListDTO = recruitmentPostService.recruitmentListByClubName(clubName);
        return ResponseEntity.ok(postListDTO);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateRecruitmentPost(@RequestBody PostUpdateDTO postUpdateDTO){
        recruitmentPostService.updateRecruitmentPost(postUpdateDTO);
        return ResponseEntity.ok("모집공고 업데이트 성공");
    }

    @GetMapping("/delete")
    public ResponseEntity<String> deleteRecruitmentPost(@RequestParam Long postId){
        recruitmentPostService.deleteRecruitmentPost(postId);
        return ResponseEntity.ok("모집공고 삭제 완료");
    }
}
