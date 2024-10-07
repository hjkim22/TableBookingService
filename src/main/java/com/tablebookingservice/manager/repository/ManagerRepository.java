package com.tablebookingservice.manager.repository;

import com.tablebookingservice.manager.entity.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<ManagerEntity, Long> {
    // 기본 기능 사용
}
