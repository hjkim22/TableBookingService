package com.tablebookingservice.store.controller;

import com.tablebookingservice.store.dto.CreateStore;
import com.tablebookingservice.store.dto.UpdateStore;
import com.tablebookingservice.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 매장 관련 API
 */
@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    /**
     * 매장 생성
     * @param request 매장 생성 요청 데이터
     * @return 생성된 매장 정보
     */
    @PostMapping("/manager/create")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public CreateStore.Response createStore(
            @RequestBody CreateStore.Request request) {

        return CreateStore.Response
                .fromStoreDto(this.storeService.createStore(request));
    }

    /**
     * 특정 매장 정보 조회
     * @param name 매장 이름
     * @return 매장 상세 정보
     */
    @GetMapping("/detail/{name}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> storeDetail(@PathVariable String name) {
        return ResponseEntity.ok(this.storeService.detailStore(name));
    }

    /**
     * 특정 매니저가 관리하는 모든 매장 조회
     * @param id 매니저 ID
     * @return 관리자가 관리하는 매장 리스트
     */
    @GetMapping("/manager/list")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> getStoreList(@RequestParam("managerId") Long id) {
        return ResponseEntity.ok(this.storeService.searchStoreList(id));
    }

    /**
     * 매장 정보 업데이트
     * @param id 매장 ID
     * @param request 매장 업데이트 요청 데이터
     * @return 업데이트된 매장 정보
     */
    @PutMapping("/manager/update/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public UpdateStore.Response updateStore(
            @PathVariable Long id,
            @RequestBody UpdateStore.Request request) {

        return UpdateStore.Response
                .fromStoreDto(this.storeService.updateStore(id, request));
    }

    /**
     * 매장 삭제
     * @param managerId 매장 관리자 ID
     * @param storeId   매장 ID
     * @return 삭제 완료 메시지
     */
    @DeleteMapping("/manager/delete")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> deleteStore(
            @RequestParam("id") Long managerId,
            @RequestParam("store") Long storeId) {

        this.storeService.deleteStore(managerId, storeId);
        return ResponseEntity.ok("매장 삭제가 완료되었습니다.");
    }
}
