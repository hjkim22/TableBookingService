package com.tablebookingservice.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}

// GlobalEntity 같은 엔티티 클래스에서 JPA 감사 기능을 사용할 수 있음
