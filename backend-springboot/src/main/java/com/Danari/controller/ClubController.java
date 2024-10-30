package com.Danari.controller;

import com.Danari.dto.ClubDetailDTO;
import com.Danari.dto.ClubResponseDTO;
import com.Danari.service.ClubService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clubs")
public class ClubController {
    @Autowired
    private ClubService clubService;

    @GetMapping("/list/all")
    @Operation(summary = "전체 동아리 목록", description = "[동아리 목록] 페이지에서 [전체] 선택 시 보일 동아리 정보 리스트")
    public ResponseEntity<List<ClubResponseDTO>> clubListAll(){
        return ResponseEntity.ok(clubService.allClubList());
    }

    @GetMapping("/list/{department}")
    @Operation(summary = "분과별 동아리 목록", description = "[동아리 목록] 페이지에서 분과 선택시 보일 해당 분과 동아리 정보 리스트")
    public ResponseEntity<List<ClubResponseDTO>> clubListByDepartment(@Parameter(description = "조회할 분과 이름") @PathVariable String department){
        return ResponseEntity.ok(clubService.clubListByDepartment(department));
    }

    @GetMapping("/details/{clubName}")
    @Operation(summary = "동아리 세부 정보", description = "[동아리 세부 정보] 페이지에 보일 동아리 세부 정보")
    public ResponseEntity<ClubDetailDTO> clubDetailByCLubName(@Parameter(description = "세부정보 조회할 동아리명") @PathVariable String clubName){
        ClubDetailDTO clubDetailDTO = clubService.clubDetailByClubName(clubName);
        return ResponseEntity.ok(clubDetailDTO);
    }

}
