package com.tablebookingservice.store.entity;

import com.tablebookingservice.global.entity.GlobalEntity;
import com.tablebookingservice.manager.entity.ManagerEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreEntity extends GlobalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private ManagerEntity manager;

    private String storeName;
    private String location;
    private String phoneNumber;
}
