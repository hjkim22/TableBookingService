package com.tablebookingservice.global.exception;

import com.tablebookingservice.global.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.tablebookingservice.global.type.ErrorCode.INTERNAL_SERVER_ERROR;
import static com.tablebookingservice.global.type.ErrorCode.INVALID_REQUEST;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ErrorResponseDto customExceptionHandler(CustomException e) {
        log.error("{} 오류가 발생했습니다.", e.getErrorCode());

        return new ErrorResponseDto(e.getErrorCode(), e.getErrorMessage());
    }

    // 데이터 무결성 위반 예외 처리
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorResponseDto dataIntegrityViolationExceptionHandler(DataIntegrityViolationException e) {
        log.error("데이터 무결성 위반 오류가 발생했습니다.", e);
        return new ErrorResponseDto(INVALID_REQUEST, INVALID_REQUEST.getDescription());
    }

    // 사용자 이름을 찾을 수 없는 예외 처리
    @ExceptionHandler(UsernameNotFoundException.class)
    public ErrorResponseDto usernameNotFoundExceptionHandler(UsernameNotFoundException e) {
        log.error("사용자를 찾을 수 없는 오류가 발생했습니다.", e);
        return new ErrorResponseDto(INVALID_REQUEST, INVALID_REQUEST.getDescription());
    }

    // 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ErrorResponseDto exceptionHandler(Exception e) {
        log.error("예외가 발생했습니다.", e);
        return new ErrorResponseDto(INTERNAL_SERVER_ERROR,
                INTERNAL_SERVER_ERROR.getDescription());
    }
}
