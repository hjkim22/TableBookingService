package com.tablebookingservice.repository;

import com.tablebookingservice.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
