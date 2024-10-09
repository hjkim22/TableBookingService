package com.tablebookingservice.user.repository;

import com.tablebookingservice.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // 사용자 이름으로 UserEntity를 찾는 메서드
    Optional<UserEntity> findByUsername(String username);

    // 사용자 이름이 존재하는지 확인하는 메서드
    boolean existsByUsername(String username);
}