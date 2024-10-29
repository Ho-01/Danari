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

    @GetMapping("/list/all") // "동아리 목록" 페이지에서 "전체" 선택 시 보일 동아리 정보 리스트
    public ResponseEntity<ClubListDTO> clubListAll(){
        ClubListDTO clubListDTO = clubService.allClubList();
        return ResponseEntity.ok(clubListDTO);
    }

    @GetMapping("/list/{department}") // "동아리 목록" 페이지에서 분과 선택시 보일 해당 분과 동아리 정보 리스트
    public ResponseEntity<ClubListDTO> clubListByDepartment(@PathVariable String department){
        ClubListDTO clubListDTO = clubService.clubListByDepartment(department);
        return ResponseEntity.ok(clubListDTO);
    }

    @GetMapping("/details/{clubName}") // "동아리 세부 정보" 페이지에 보일 동아리 세부 정보
    public ResponseEntity<ClubDetailDTO> clubDetailByCLubName(@PathVariable String clubName){
        ClubDetailDTO clubDetailDTO = clubService.clubDetailByClubName(clubName);
        return ResponseEntity.ok(clubDetailDTO);
    }

}
