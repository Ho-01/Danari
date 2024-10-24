package com.Danari.controller;

import com.Danari.domain.Club;
import com.Danari.dto.ClubDTO;
import com.Danari.dto.ClubDetailDTO;
import com.Danari.dto.ClubListDTO;
import com.Danari.dto.ClubUpdateDTO;
import com.Danari.repository.ClubJpaRepository;
import com.Danari.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/club")
public class ClubController {
    @Autowired
    private ClubService clubService;

    @GetMapping("/all") // "동아리 목록" 페이지에서 "전체" 선택 시 보일 동아리 정보 리스트
    public ResponseEntity<ClubListDTO> clubListAll(){
        ClubListDTO clubListDTO = clubService.allClubList();
        return ResponseEntity.ok(clubListDTO);
    }

    @GetMapping("/department") // "동아리 목록" 페이지에서 분과 선택시 보일 해당 분과 동아리 정보 리스트
    public ResponseEntity<ClubListDTO> clubListByDepartment(@RequestParam String department){
        ClubListDTO clubListDTO = clubService.clubListByDepartment(department);
        return ResponseEntity.ok(clubListDTO);
    }

    @GetMapping("/details") // "동아리 세부 정보" 페이지에 보일 동아리 세부 정보
    public ResponseEntity<ClubDetailDTO> clubDetailByCLubName(@RequestParam String clubName){
        ClubDetailDTO clubDetailDTO = clubService.clubDetailByClubName(clubName);
        return ResponseEntity.ok(clubDetailDTO);
    }

    @PostMapping("/update") // 등급이 MEMBERGRADE.PRESIDENT 인 동아리의 정보를 수정
    public ResponseEntity<String> updateClub(@RequestBody ClubUpdateDTO clubUpdateDTO){
        clubService.updateClub(clubUpdateDTO);
        return ResponseEntity.ok("동아리정보 수정 성공");
    }


    @PostMapping("/create") // 관리자 페이지(미구현)에서 새로운 동아리 정보 등록시 필요.
    public ResponseEntity<String> newClubRegister(@RequestBody ClubDTO clubDTO){
        clubService.newClubRegister(clubDTO);
        return ResponseEntity.ok("새 동아리정보 등록 성공");
    }
}
