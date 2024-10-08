package com.tablebookingservice.reservation.repository;

import com.tablebookingservice.reservation.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    /**
     * 매장 예약 여부 파악
     * @param reservationTime : 매장 예약 시간
     * @return 예약 여부 유/무 리턴
     */
    @Query("SELECT COUNT(r) > 0 " +
            "FROM ReservationEntity r " +
            "WHERE r.reservationDate = :#{#reservationTime.toLocalDate()} " +
            "AND r.reservationTime = :#{#reservationTime.toLocalTime()}")
    boolean existReservationTime(@Param("reservationTime") LocalDateTime reservationTime);

    /**
     * 매장 예약 정보 확인
     * @param id: 매니저 아이디
     * @return 해당 매니저의 모든 예약 리스트
     */
    @Query(" SELECT r FROM ReservationEntity r " +
            " WHERE r.store.manager.id = :id " +
            " ORDER BY r.reservationDate ")
    List<ReservationEntity> findAllByManagerReservation(@Param("id") Long id);
}
