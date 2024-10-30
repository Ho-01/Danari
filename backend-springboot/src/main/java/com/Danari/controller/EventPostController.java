package com.Danari.controller;

import com.Danari.dto.PostCreateDTO;
import com.Danari.dto.PostResponseDTO;
import com.Danari.dto.PostUpdateDTO;
import com.Danari.service.EventPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event-posts")
public class EventPostController {
    @Autowired
    EventPostService eventPostService;

    @PostMapping
    @Operation(summary = "동아리 행사글 작성", description = "[동아리 행사 정보] 페이지에서 새로운 행사글 등록 시 사용")
    public ResponseEntity<String> newEventPost(@RequestBody PostCreateDTO postCreateDTO){
        // 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // 현재 로그인된 사용자의 username

        eventPostService.newEventPost(postCreateDTO);
        return ResponseEntity.ok("행사글 저장 완료");
    }

    @GetMapping("/list/{clubName}")
    @Operation(summary = "해당 동아리의 행사 목록 조회", description = "[동아리 행사 정보 목록] 페이지에서 행사 목록 조회 시 사용")
    public ResponseEntity<List<PostResponseDTO>> EventListByClubName(@Parameter(description = "행사 조회할 동아리명") @PathVariable String clubName){
        return ResponseEntity.ok(eventPostService.eventListByClubName(clubName));
    }

    @GetMapping("/{postId}")
    @Operation(summary = "동아리 행사 세부내용 조회", description = "[동아리 행사 정보] 페이지에서 행사 세부 조회 시 사용")
    public ResponseEntity<PostResponseDTO> EventPostById(@Parameter(description = "세부조회할 행사의 postId") @PathVariable Long postId){
        PostResponseDTO postResponseDTO = eventPostService.eventPostById(postId);
        return ResponseEntity.ok(postResponseDTO);
    }

    @PutMapping("/{postId}")
    @Operation(summary = "동아리 행사 내용 수정", description = "[수정 페이지] 에서 행사 수정 시 사용")
    public ResponseEntity<String> updateEventPost(@Parameter(description = "내용 수정할 행사의 postId") @PathVariable Long postId, @RequestBody PostUpdateDTO postUpdateDTO){
        // 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // 현재 로그인된 사용자의 username

        // 게시글 작성자 확인 > 맞지 않으면 수정불가
        PostResponseDTO postResponseDTO = eventPostService.eventPostById(postUpdateDTO.getPostId());
        if(!postResponseDTO.getUsername().equals(currentUsername)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("게시글 수정 권한이 없습니다");
        }
        // 클라이언트 측에서 권한 없는 글에 대한 게시글 수정 버튼을 비활성화하면 되지만,
        // 혹시 모를 잘못된 수정 요청에 대응하기 위해 추가 인증 과정을 거칩니다.

        postUpdateDTO.setPostId(postId);
        eventPostService.updateEventPost(postUpdateDTO);
        return ResponseEntity.ok("행사 업데이트 성공");
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "동아리 행사 삭제", description = "[동아리 행사 정보] 페이지에서 본인이 작성한 동아리 행사 삭제 시 사용")
    public ResponseEntity<String> deleteEventPost(@Parameter(description = "삭제할 행사의 postId") @PathVariable Long postId){
        // 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // 현재 로그인된 사용자의 username

        // 게시글 작성자 확인 > 맞지 않으면 삭제불가
        PostResponseDTO postResponseDTO = eventPostService.eventPostById(postId);
        if(!postResponseDTO.getUsername().equals(currentUsername)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("게시글 삭제 권한이 없습니다");
        }
        // 클라이언트 측에서 권한 없는 글에 대한 게시글 삭제 버튼을 비활성화하면 되지만,
        // 혹시 모를 잘못된 삭제 요청에 대응하기 위해 추가 인증 과정을 거칩니다.

        eventPostService.deleteEventPost(postId);
        return ResponseEntity.ok("행사 삭제 완료");
    }
}
