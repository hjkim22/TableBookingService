package com.tablebookingservice.user.controller;

import com.tablebookingservice.user.dto.RegisterUserDto;
import com.tablebookingservice.user.dto.UserDto;
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
     *
     * @param request 회원 가입에 필요한 사용자 정보가 담긴 DTO
     * @return ResponseEntity에 등록된 사용자 정보를 담아 반환
     */
    @PostMapping("/register/user")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto request) {
        // 사용자 정보 기반으로 회원가입 후 DTO 를 변환하여 응답
//        return ResponseEntity
//                .ok()
//                .body(request.fromUserDto(this.userService.register(request)));
        UserDto userDto = this.userService.register(request);
        return ResponseEntity.ok(userDto);
    }

    /**
     * 회원 본인 정보 조회
     *
     * @return ResponseEntity에 사용자의 정보가 담겨 반환
     * @throws CustomException 사용자 정보를 찾을 수 없는 경우 예외 발생
     */
    @GetMapping("/user/info")
    // 메서드 호출전 사용자 권한 체크
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    public ResponseEntity<?> getCustomerInfo(@RequestParam("id") Long id) {
        return ResponseEntity.ok(this.userService.memberDetail(id));
    }
}
