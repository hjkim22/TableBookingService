package com.tablebookingservice.manager.controller;

import com.tablebookingservice.manager.dto.RegisterManagerDto;
import com.tablebookingservice.manager.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    /**
     * 회원 가입
     * @param request : 회원 가입
     * @return register 정보
     */
    @PostMapping("/register/manager")
    public ResponseEntity<?> register(@RequestBody RegisterManagerDto request) {
        return ResponseEntity.ok().body(
                request.from(this.managerService.register(request)));
    }

    /**
     * 회원 정보 조회
     * @param id 사용자 아이디
     * @return 사용자 정보
     */
    @GetMapping("/partner/info")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> getManagerInfo(@RequestParam("id") Long id) {
        return ResponseEntity.ok(this.managerService.memberDetail(id));
    }
}
