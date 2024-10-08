package com.tablebookingservice.store.service;

import com.tablebookingservice.global.exception.CustomException;
import com.tablebookingservice.manager.entity.ManagerEntity;
import com.tablebookingservice.manager.repository.ManagerRepository;
import com.tablebookingservice.store.dto.CreateStore;
import com.tablebookingservice.store.dto.StoreDto;
import com.tablebookingservice.store.dto.UpdateStore;
import com.tablebookingservice.store.entity.StoreEntity;
import com.tablebookingservice.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.tablebookingservice.global.type.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final ManagerRepository managerRepository;

    /**
     * 매장 생성
     * @param request 매장 생성 요청 데이터
     * @return 생성된 매장의 정보를 담은 StoreDto
     * @throws CustomException 매니저가 존재하지 않거나, 이미 동일한 이름의 매장이 존재하는 경우
     */
    @Override
    @Transactional
    public StoreDto createStore(CreateStore.Request request) {
        log.info("매장 생성 시작: storeName = {}", request.getStoreName());

        ManagerEntity manager = this.managerRepository.findById(request.getManagerId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if (this.storeRepository.existsByStoreName(request.getStoreName())) {
            throw new CustomException(ALREADY_EXIST_STORE);
        }

        log.info("매장 생성 완료");

        return StoreDto.fromEntity(this.storeRepository.save(StoreEntity.builder()
                .manager(manager)
                .storeName(request.getStoreName())
                .location(request.getLocation())
                .phoneNumber(request.getPhoneNumber())
                .build()));
    }

    /**
     * 매장 정보 조회
     * @param name 매장 이름
     * @return 매장의 정보를 담은 StoreDto
     * @throws CustomException 매장이 존재하지 않는 경우
     */
    @Override
    public StoreDto detailStore(String name) {
        log.info("매장 정보 요청: storeName = {}", name);
        StoreEntity store = checkStoreName(name);
        return StoreDto.fromEntity(store);
    }

    /**
     * 매니저가 관리하는 모든 매장 조회
     * @param managerId 매니저 ID
     * @return 매장의 정보를 담은 StoreDto 리스트
     * @throws CustomException 매장이 존재하지 않는 경우
     */
    @Override
    public List<StoreDto> searchStoreList(Long managerId) {
        log.info("매장 리스트 조회: managerId = {}", managerId);

        List<StoreEntity> storeList =
                this.storeRepository.findStoreByManagerId(managerId);

        if (storeList.isEmpty()) {
            throw new CustomException(STORE_NOT_FOUND);
        }

        log.info("매장 리스트 조회 완료: managerId = {}", managerId);
        return storeList.stream()
                .map(StoreDto::fromEntity).collect(Collectors.toList());
    }

    /**
     * 매장 정보 업데이트
     * @param id      매장 ID
     * @param request 매장 업데이트 요청 데이터
     * @return 업데이트된 매장의 정보를 담은 StoreDto
     * @throws CustomException 매장이 존재하지 않거나, 매장의 매니저와 일치하지 않는 경우
     */
    @Override
    @Transactional
    public StoreDto updateStore(Long id, UpdateStore.Request request) {
        log.info("매장 정보 업데이트 요청: storeId = {}, storeName = {}", id, request.getStoreName());

        StoreEntity store = this.storeRepository.findById(id)
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

        if (!store.getManager().getId().equals(request.getManagerId())) {
            throw new CustomException(STORE_NOT_MATCH_MANAGER);
        }

        store.setStoreName(request.getStoreName());
        store.setLocation(request.getLocation());

        log.info("매장 정보 업데이트 완료: storeName = {}", store.getStoreName());
        return StoreDto.fromEntity(this.storeRepository.save(store));
    }

    /**
     * 매장 삭제
     * @param managerId 매니저 ID
     * @param storeId   매장 ID
     * @throws CustomException 매장이 존재하지 않거나, 매장의 매니저와 일치하지 않는 경우
     */
    @Override
    @Transactional
    public void deleteStore(Long managerId, Long storeId) {
        log.info("매장 삭제 요청: managerId = {}, storeId = {}", managerId, storeId);

        StoreEntity store = this.storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

        if (!store.getManager().getId().equals(managerId)) {
            throw new CustomException(STORE_NOT_MATCH_MANAGER);
        }

        this.storeRepository.delete(store);
        log.info("매장 삭제 완료: storeName = {}", store.getStoreName());
    }

    /**
     * 매장 이름으로 매장을 조회, 예외 처리
     * @param name 매장 이름
     * @return 매장 엔티티
     * @throws CustomException 매장이 존재하지 않는 경우
     */
    private StoreEntity checkStoreName(String name) {
        return this.storeRepository.findByStoreName(name)
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));
    }

}
