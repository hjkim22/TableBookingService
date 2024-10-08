package com.tablebookingservice.store.service;

import com.tablebookingservice.store.dto.CreateStore;
import com.tablebookingservice.store.dto.StoreDto;
import com.tablebookingservice.store.dto.UpdateStore;

import java.util.List;

public interface StoreService {
    StoreDto createStore(CreateStore.Request request);
    StoreDto detailStore(String name);
    StoreDto updateStore(Long id, UpdateStore.Request request);
    void deleteStore(Long managerId, Long storeId);
    List<StoreDto> searchStoreList(Long id);
}