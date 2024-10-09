package com.tablebookingservice.auth.controller;


import com.tablebookingservice.auth.dto.LoginInput;
import com.tablebookingservice.auth.security.TokenProvider;
import com.tablebookingservice.auth.service.AuthService;
import com.tablebookingservice.manager.entity.ManagerEntity;
import com.tablebookingservice.user.entity.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @PostMapping("/manager")
    public ResponseEntity<?> managerLogin(@RequestBody @Valid LoginInput request){
        ManagerEntity manager = this.authService.authenticateManager(request);
        return ResponseEntity.ok(
                this.tokenProvider.createToken(
                        manager.getUsername(),
                        manager.getMemberType())
        );
    }

    @PostMapping("/customer")
    public ResponseEntity<?> userLogin(@RequestBody @Valid LoginInput request){
        UserEntity user = this.authService.authenticateCustomer(request);
        return ResponseEntity.ok(
                this.tokenProvider.createToken(
                        user.getUsername(),
                        user.getMemberType())
        );
    }
}