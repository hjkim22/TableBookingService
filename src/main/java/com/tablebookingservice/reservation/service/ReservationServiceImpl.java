package com.tablebookingservice.reservation.service;

import com.tablebookingservice.global.exception.CustomException;
import com.tablebookingservice.reservation.dto.CreateReservation;
import com.tablebookingservice.reservation.dto.ReservationDto;
import com.tablebookingservice.reservation.dto.UpdateArrival;
import com.tablebookingservice.reservation.dto.UpdateReservation;
import com.tablebookingservice.reservation.entity.ReservationEntity;
import com.tablebookingservice.reservation.repository.ReservationRepository;
import com.tablebookingservice.reservation.type.ReservationStatus;
import com.tablebookingservice.store.entity.StoreEntity;
import com.tablebookingservice.store.repository.StoreRepository;
import com.tablebookingservice.user.entity.UserEntity;
import com.tablebookingservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.tablebookingservice.global.type.ErrorCode.*;
import static com.tablebookingservice.reservation.type.ArrivalStatus.ARRIVED;
import static com.tablebookingservice.reservation.type.ArrivalStatus.READY;
import static com.tablebookingservice.reservation.type.ReservationStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;
    private final UserRepository customerRepository;

    @Override
    @Transactional
    public ReservationDto createReservation(CreateReservation.Request request) {
        log.info("예약 등록 시작: {}", request.toString());

        // 매장 / 사용자 조회
        StoreEntity store = this.storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

        UserEntity user = this.customerRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        LocalDateTime reserveTime = LocalDateTime.of(
                request.getReservationDate(), request.getReservationTime());

        // 예약 시간 중복 체크
        boolean exists = this.reservationRepository.existReservationTime(reserveTime);
        if (exists) {
            log.info(ALREADY_RESERVED.getDescription());
            throw new CustomException(ALREADY_RESERVED);
        }

        ReservationEntity reservation = this.reservationRepository.save(ReservationEntity.builder()
                .user(user)
                .store(store)
                .reservationStatus(STANDBY)
                .arrivalStatus(READY)
                .reservationDate(request.getReservationDate())
                .reservationTime(request.getReservationTime())
                .build());

        log.info("예약 등록 완료");
        return ReservationDto.fromEntity(reservation);
    }

    @Override
    @Transactional
    public ReservationDto updateReservation(Long reservationId, UpdateReservation.Request request) {
        log.info("예약 승인 여부 변경");

        ReservationEntity reservation = this.reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));

        ReservationStatus status = reservation.getReservationStatus();
        if (status.equals(request.getReservationStatus())) {
            log.info(RESERVATION_STATUS_CHECK_ERROR.getDescription());
            throw new CustomException(RESERVATION_STATUS_CHECK_ERROR);
        }

        reservation.setReservationStatus(request.getReservationStatus());
        log.info("예약 승인 여부 변경 완료");

        return ReservationDto.fromEntity(
                // this. 제거 / save 호출을 직접 한 것으로 변경
                reservationRepository.save(reservation));
    }

    @Override
    public List<ReservationEntity> searchReservation(Long id) {
        log.info("예약 요청 목록 조회");

        List<ReservationEntity> reservations
                = this.reservationRepository.findAllByManagerReservation(id);

        if (reservations.isEmpty()) {
            log.info(RESERVATION_NOT_FOUND.getDescription());
            throw new CustomException(RESERVATION_NOT_FOUND);
        }

        return reservations;
    }

    @Override
    @Transactional
    public ReservationDto updateArrival(Long reservationId, UpdateArrival.Request request) {
        log.info("예약자 도착 여부 변경 시작");
        ReservationEntity reservation = this.reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));

        validationReservation(reservation, request.getArrivalTime().toLocalTime());

        reservation.setArrivalStatus(ARRIVED);
        reservation.setReservationStatus(USE_COMPLETED);

        log.info("예약자 도착 여부 변경 완료");

        return ReservationDto.fromEntity(
                reservationRepository.save(reservation));
    }

    @Override
    @Transactional
    public ReservationDto cancelReservation(Long reservationId) {
        log.info("예약 상태 취소 시작");

        ReservationEntity reservation = this.reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));

        reservation.setReservationStatus(CANCELED);
        log.info("예약 상태 취소 완료");

        return ReservationDto.fromEntity(
                reservationRepository.save(reservation));
    }

    private void validationReservation(ReservationEntity reservation, LocalTime arrivalTime) {
        // 예약 상태 유효성 검사
        if (!reservation.getReservationStatus().equals(ReservationStatus.APPROVAL)) {
            throw new CustomException(RESERVATION_STATUS_CHECK_ERROR);
        }
        // 도착 시간 유효성 검사
        else if (arrivalTime.isAfter(reservation.getReservationTime())) {
            throw new CustomException(RESERVATION_TIME_EXCEEDED);
        }
        // 도착 시간 10분 전 확인
        else if (arrivalTime.isBefore(reservation.getReservationTime().minusMinutes(10L))) {
            throw new CustomException(CHECK_IT_10_MINUTES_BEFORE_THE_RESERVATION_TIME);
        }
    }
}