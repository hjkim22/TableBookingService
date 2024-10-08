package com.tablebookingservice.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}

// GlobalEntity를 상속받는 다른 엔티티에서도
// 생성일과 수정일을 자동으로 관리할 수 있음.
