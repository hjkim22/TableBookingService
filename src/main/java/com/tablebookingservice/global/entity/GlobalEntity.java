package com.tablebookingservice.global.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// JPA에서 사용되는 공통 필드 정의 상위 클래스

@Getter
@MappedSuperclass // 하위 클래스 테이블 매핑
@EntityListeners(AuditingEntityListener.class) // JPA 엔티티 리스너 설정
public class GlobalEntity {

    @CreatedDate // 생성일 자동 설정
    private LocalDateTime createdAt;

    @LastModifiedDate // 수정일 자동 설정
    private LocalDateTime updatedAt;
}
