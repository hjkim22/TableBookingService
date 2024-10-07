package com.tablebookingservice.user.controller;

import com.tablebookingservice.user.dto.RegisterUser;
import com.tablebookingservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원 가입
     * @param request : 회원 가입
     * @return register 정보
     */
    @PostMapping("/register/user")
    public ResponseEntity<?> register(@RequestBody RegisterUser request) {
        // 사용자 정보 기반으로 회원가입 후 DTO를 변환하여 응답
        return ResponseEntity
                .ok()
                .body(request.fromUserDto(this.userService.register(request)));
    }

    /**
     * 회원 본인 정보 조회 (사용자 본인 및 매니저 모두 확인 가능)
     * @param id 사용자 아이디
     * @return 사용자 정보
     */
    @GetMapping("/customer/info")
    // 메서드 호출전 사용자 권한 체크
    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    public ResponseEntity<?> getCustomerInfo(@RequestParam("id") Long id) {
        return ResponseEntity.ok(this.userService.memberDetail(id));
    }
}
