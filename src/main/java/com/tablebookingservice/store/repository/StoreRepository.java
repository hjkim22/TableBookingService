package com.tablebookingservice.store.repository;

import com.tablebookingservice.store.entity.StoreEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

    @Cacheable("storeExistsCache") // 캐시 저장
    boolean existsByStoreName(String name);

    @Cacheable("storeCache")
    Optional<StoreEntity> findByStoreName(String storeName);

    @Cacheable("managerStoresCache")
    @Query("select s from StoreEntity s where s.manager.id = :managerId")
    List<StoreEntity> findStoreByManagerId(@Param("managerId") Long managerId);
}
