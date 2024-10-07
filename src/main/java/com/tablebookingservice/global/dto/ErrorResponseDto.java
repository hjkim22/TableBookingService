package com.tablebookingservice.global.dto;

import com.tablebookingservice.global.type.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {
    private ErrorCode errorCode;
    private String errorMessage;
}
