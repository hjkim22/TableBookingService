package com.tablebookingservice.reservation.controller;

import com.tablebookingservice.reservation.dto.CreateReservation;
import com.tablebookingservice.reservation.dto.SearchReservation;
import com.tablebookingservice.reservation.dto.UpdateArrival;
import com.tablebookingservice.reservation.dto.UpdateReservation;
import com.tablebookingservice.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 예약 관련 API
 */
@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    /**
     * 예약 생성
     * @param request 예약 생성 요청 DTO
     * @return 생성된 예약 정보
     */
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_USER')")
    public CreateReservation.Response createReservation(
            @RequestBody CreateReservation.Request request) {
        return CreateReservation.Response.fromReservationDto(reservationService.createReservation(request));
    }

    /**
     * 매니저가 예약 승인
     * @param id 예약 ID
     * @param request 예약 업데이트 요청 DTO
     * @return 업데이트된 예약 정보
     */
    @PutMapping("/manager/approval/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public UpdateReservation.Response updateReservation(
            @PathVariable Long id,
            @RequestBody UpdateReservation.Request request) {

        return UpdateReservation.Response.fromReservationDto(reservationService.updateReservation(id, request));
    }

    /**
     * 매니저가 자신의 예약 목록 조회
     * @param id 매니저 ID
     * @return 예약 목록
     */
    @GetMapping("/manager/reservation-list/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public SearchReservation getReservationList(@PathVariable Long id) {
        return SearchReservation.fromReservationDto(reservationService.searchReservation(id));
    }

    /**
     * 키오스크에서 예약자의 도착 여부 업데이트
     * @param id 예약 ID
     * @param request 도착 업데이트 요청 DTO
     * @return 업데이트된 도착 정보
     */
    @PutMapping("/kiosk/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public UpdateArrival.Response updateArrivalKiosk(
            @PathVariable Long id,
            @RequestBody UpdateArrival.Request request) {

        return UpdateArrival.Response.fromReservationDto(reservationService.updateArrival(id, request));
    }

    /**
     * 예약 취소
     * @param reservationId 예약 ID
     * @return 취소된 예약 정보
     */
    @PutMapping("/cancel")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    public ResponseEntity<?> cancelReservation(
            @RequestParam(name = "reservationId") Long reservationId) {

        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }
}
