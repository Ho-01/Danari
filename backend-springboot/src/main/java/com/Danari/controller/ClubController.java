package com.Danari.controller;

import com.Danari.domain.Club;
import com.Danari.dto.ClubDetailDTO;
import com.Danari.dto.ClubListDTO;
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

    @GetMapping("/all")
    public ResponseEntity<ClubListDTO> clubListAll(){
        ClubListDTO clubListDTO = clubService.allClubList();
        return ResponseEntity.ok(clubListDTO);
    }

    @GetMapping("/department")
    public ResponseEntity<ClubListDTO> clubListByDepartment(@RequestParam String department){
        ClubListDTO clubListDTO = clubService.clubListByDepartment(department);
        return ResponseEntity.ok(clubListDTO);
    }

    @GetMapping("/details")
    public ResponseEntity<ClubDetailDTO> clubDetailByCLubName(@RequestParam String clubName){
        ClubDetailDTO clubDetailDTO = clubService.clubDetailByClubName(clubName);
        return ResponseEntity.ok(clubDetailDTO);
    }
}
