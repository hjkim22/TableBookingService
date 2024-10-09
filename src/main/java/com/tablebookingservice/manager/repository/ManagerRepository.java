package com.tablebookingservice.manager.repository;

import com.tablebookingservice.manager.entity.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<ManagerEntity, Long> {
    // 사용자 이름으로 ManagerEntity를 찾는 메서드
    Optional<ManagerEntity> findByUsername(String username);

    // 사용자 이름이 존재하는지 확인하는 메서드
    boolean existsByUsername(String username);
}
