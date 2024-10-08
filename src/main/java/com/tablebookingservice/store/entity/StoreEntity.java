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

    /*
        @Builder.Default
         필드의 기본값 지정(객체를 생성할 때도 기본적으로 빈 리스트로 설정)

        fetch = FetchType.LAZY
        처음에는 불러오지 않고, 나중에 실제로 접근할 때 데이터베이스에서 해당 데이터를 가져옴
        성능최적를 위해 사용

        orphanRemoval = true
        StoreEntity에서 예약이 제거되면 데이터베이스에서도 자동으로 삭제됩니다.
    */
}
