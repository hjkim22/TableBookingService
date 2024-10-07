package com.tablebookingservice.global.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND(BAD_REQUEST.value(), "사용자가 없습니다."),
    MANAGER_NOT_FOUND(BAD_REQUEST.value(), "매니저가 없습니다."),
    PASSWORD_NOT_MATCH(BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다"),
    ALREADY_EXIST_USER(BAD_REQUEST.value(), "이미 가입된 회원입니다."),
    ALREADY_LOGIN_USER(BAD_REQUEST.value(), "이미 로그인이 되어 있는 상태입니다."),
    USER_AUTHORITY_NOT_MATCH(BAD_REQUEST.value(), "사용자가 일치 하지 않습니다.");

    private final int statusCode;
    private final String description;
}
