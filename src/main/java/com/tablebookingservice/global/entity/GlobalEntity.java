package com.tablebookingservice.global.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
// 직접 테이블과 매핑시키지 않고, 상속받는 하위 클래스들이 이 클래스 필드를 상속받아서 해당 필드를 자신의 테이블과 매핑
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // JPA 엔티티 리스너 설정
public class GlobalEntity {

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
