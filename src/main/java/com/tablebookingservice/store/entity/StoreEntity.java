package com.tablebookingservice.store.entity;

import com.tablebookingservice.global.entity.GlobalEntity;
import com.tablebookingservice.manager.entity.ManagerEntity;
import com.tablebookingservice.reservation.entity.ReservationEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Builder.Default
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ReservationEntity> reservationList = new ArrayList<>();

    // TODO
//    @Builder.Default
//    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, orphanRemoval = true)
//    private List<Review> reviewList = new ArrayList<>();
}
