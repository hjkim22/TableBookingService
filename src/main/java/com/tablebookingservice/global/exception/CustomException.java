package com.tablebookingservice.global.exception;

import com.tablebookingservice.global.type.ErrorCode;
import lombok.Getter;

// 커스텀 예외 클래스
@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String errorMessage;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getDescription()); // 부모 클래스 메시지 초기화
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
