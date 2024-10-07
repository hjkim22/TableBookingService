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

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('CUSTOMER')")
    public CreateReservation.Response createReservation(
            @RequestBody CreateReservation.Request request) {
        return CreateReservation.Response.of(this.reservationService.createReservation(request));
    }

    @PutMapping("/manager/approval/{id}")
    @PreAuthorize("hasRole('PARTNER')")
    public UpdateReservation.Response updateReservation(
            @PathVariable Long id,
            @RequestBody UpdateReservation.Request request) {

        return UpdateReservation.Response.of(
                this.reservationService.updateReservation(id, request));
    }

    @GetMapping("/manager/reservation-list/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public SearchReservation getReservationList(@PathVariable Long id) {
        return SearchReservation.of(
                this.reservationService.searchReservation(id));
    }

    @PutMapping("/kiosk/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public UpdateArrival.Response updateArrivalKiosk(
            @PathVariable Long id,
            @RequestBody UpdateArrival.Request request) {

        return UpdateArrival.Response.of(
                this.reservationService.updateArrival(id, request));
    }

    @PutMapping("/cancel")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    public ResponseEntity<?> cancelReservation(
            @RequestParam(name = "reservationid") Long reservationId) {

        return ResponseEntity.ok(
                this.reservationService.cancelReservation(reservationId));
    }
}
