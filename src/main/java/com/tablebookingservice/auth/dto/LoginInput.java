package com.tablebookingservice.auth.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginInput {

    @Column
    private String username;

    @NotBlank
    private String password;
}
