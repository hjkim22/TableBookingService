package com.tablebookingservice.store.service;

import com.tablebookingservice.global.exception.CustomException;
import com.tablebookingservice.manager.entity.ManagerEntity;
import com.tablebookingservice.manager.repository.ManagerRepository;
import com.tablebookingservice.store.dto.CreateStore;
import com.tablebookingservice.store.dto.StoreDto;
import com.tablebookingservice.store.dto.UpdateStore;
import com.tablebookingservice.store.entity.StoreEntity;
import com.tablebookingservice.store.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.tablebookingservice.global.type.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreServiceImplTest {

    @InjectMocks
    private StoreServiceImpl storeService;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private ManagerRepository managerRepository;

    private ManagerEntity manager;

    @BeforeEach
    void setUp() {
        manager = new ManagerEntity();
        manager.setId(1L);
    }

    @Test
    void testCreateStore_Success() {
        CreateStore.Request request = new CreateStore.Request();
        request.setManagerId(1L);
        request.setStoreName("Test Store");
        request.setLocation("Test Location");
        request.setPhoneNumber("123-456-7890");

        when(managerRepository.findById(request.getManagerId())).thenReturn(Optional.of(manager));
        when(storeRepository.existsByStoreName(request.getStoreName())).thenReturn(false);
        when(storeRepository.save(any(StoreEntity.class))).thenReturn(new StoreEntity());

        StoreDto storeDto = storeService.createStore(request);

        assertNotNull(storeDto);
        verify(storeRepository, times(1)).save(any(StoreEntity.class));
    }

    @Test
    void testCreateStore_ManagerNotFound() {
        CreateStore.Request request = new CreateStore.Request();
        request.setManagerId(1L);
        request.setStoreName("Test Store");

        when(managerRepository.findById(request.getManagerId())).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> storeService.createStore(request));
        assertEquals(USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void testCreateStore_StoreAlreadyExists() {
        CreateStore.Request request = new CreateStore.Request();
        request.setManagerId(1L);
        request.setStoreName("Test Store");

        when(managerRepository.findById(request.getManagerId())).thenReturn(Optional.of(manager));
        when(storeRepository.existsByStoreName(request.getStoreName())).thenReturn(true);

        CustomException exception = assertThrows(CustomException.class, () -> storeService.createStore(request));
        assertEquals(ALREADY_EXIST_STORE, exception.getErrorCode());
    }

    @Test
    void testDetailStore_Success() {
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setStoreName("Test Store");

        when(storeRepository.findByStoreName("Test Store")).thenReturn(Optional.of(storeEntity));

        StoreDto storeDto = storeService.detailStore("Test Store");

        assertEquals("Test Store", storeDto.getStoreName());
    }

    @Test
    void testDetailStore_StoreNotFound() {
        when(storeRepository.findByStoreName("Test Store")).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> storeService.detailStore("Test Store"));
        assertEquals(STORE_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void testSearchStoreList_Success() {
        when(storeRepository.findStoreByManagerId(1L)).thenReturn(Collections.singletonList(new StoreEntity()));

        List<StoreDto> stores = storeService.searchStoreList(1L);

        assertFalse(stores.isEmpty());
    }

    @Test
    void testSearchStoreList_NoStoresFound() {
        when(storeRepository.findStoreByManagerId(1L)).thenReturn(Collections.emptyList());

        CustomException exception = assertThrows(CustomException.class, () -> storeService.searchStoreList(1L));
        assertEquals(STORE_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void testUpdateStore_Success() {
        UpdateStore.Request request = new UpdateStore.Request();
        request.setManagerId(1L);
        request.setStoreName("Updated Store");
        request.setLocation("Updated Location");

        StoreEntity store = new StoreEntity();
        store.setId(1L);
        store.setManager(manager);
        store.setStoreName("Old Store");

        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        StoreEntity updatedStore = new StoreEntity();
        updatedStore.setStoreName(request.getStoreName());
        updatedStore.setLocation(request.getLocation());

        when(storeRepository.save(store)).thenReturn(updatedStore);

        StoreDto storeDto = storeService.updateStore(1L, request);

        assertEquals("Updated Store", storeDto.getStoreName());
        verify(storeRepository).save(store);
    }

    @Test
    void testUpdateStore_StoreNotFound() {
        UpdateStore.Request request = new UpdateStore.Request();
        request.setManagerId(1L);

        when(storeRepository.findById(1L)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> storeService.updateStore(1L, request));
        assertEquals(STORE_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void testUpdateStore_ManagerNotMatch() {
        UpdateStore.Request request = new UpdateStore.Request();
        request.setManagerId(2L);

        StoreEntity store = new StoreEntity();
        store.setId(1L);
        store.setManager(manager);

        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));

        CustomException exception = assertThrows(CustomException.class, () -> storeService.updateStore(1L, request));
        assertEquals(STORE_NOT_MATCH_MANAGER, exception.getErrorCode());
    }

    @Test
    void testDeleteStore_Success() {
        StoreEntity store = new StoreEntity();
        store.setId(1L);
        store.setManager(manager);

        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));

        storeService.deleteStore(1L, 1L);

        verify(storeRepository).delete(store);
    }

    @Test
    void testDeleteStore_StoreNotFound() {
        when(storeRepository.findById(1L)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> storeService.deleteStore(1L, 1L));
        assertEquals(STORE_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void testDeleteStore_ManagerNotMatch() {
        StoreEntity store = new StoreEntity();
        store.setId(1L);
        store.setManager(manager);

        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));

        CustomException exception = assertThrows(CustomException.class, () -> storeService.deleteStore(2L, 1L));
        assertEquals(STORE_NOT_MATCH_MANAGER, exception.getErrorCode());
    }
}
